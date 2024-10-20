package com.example.pdfreader_kotlin.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.pdfreader_kotlin.database.FileDatabase
import com.example.pdfreader_kotlin.model.ModelFileItem
import com.example.pdfreader_kotlin.utlis.FileDAO
import kotlinx.coroutines.launch
import java.io.File

class FileViewModel(application: Application) : AndroidViewModel(application) {

    private val fileDao: FileDAO = FileDatabase.getDatabase(application).fileDao()
    val favoriteFiles: LiveData<List<ModelFileItem>> = fileDao.getAllFavoriteFiles()

    private val filesLiveData = MutableLiveData<List<ModelFileItem>>()
    val fileItems: LiveData<List<ModelFileItem>> get() = filesLiveData

    private var currentSortOrder: String = "az"

    fun loadFiles(directory: File) {
        if (directory.exists() && directory.isDirectory) {
            val fileItemsList = mutableListOf<ModelFileItem>()
            searchFiles(directory, fileItemsList)
            filesLiveData.value = fileItemsList
        }
    }

    private fun searchFiles(directory: File, fileItemsList: MutableList<ModelFileItem>) {
        val fileExtensions = setOf("pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx")
        directory.listFiles()?.forEach { file ->
            if (file.isDirectory) {
                searchFiles(file, fileItemsList)
            } else if (file.extension in fileExtensions) {
                val modelFileItem = ModelFileItem(
                    name = file.name,
                    path = file.absolutePath,
                    type = file.extension.lowercase(),
                    lastModified = file.lastModified(),
                    size = file.length()
                )
                fileItemsList.add(modelFileItem) // Thêm vào danh sách

            }
        }
    }

    fun setSortOrder(order: String) {
        currentSortOrder = order
        filesLiveData.value?.let {
            sortItems(it, order)
        }
    }

    private fun sortItems(files: List<ModelFileItem>, order: String) {
        val sortedFiles = when (order) {
            "az" -> files.sortedBy { it.name }
            "za" -> files.sortedByDescending { it.name }
            "nto" -> files.sortedByDescending { it.lastModified }
            "otn" -> files.sortedBy { it.lastModified }
            "bts" -> files.sortedByDescending { it.size }
            "stb" -> files.sortedBy { it.size }
            else -> files
        }
        filesLiveData.value = sortedFiles
    }

    fun deleteFile(file: ModelFileItem): Boolean {
        val fileToDelete = File(file.path)
        val success = fileToDelete.delete()
        if (success) {
            // cập nhật danh sách file sau khi xóa
            val updatedList = filesLiveData.value?.filter { it.path != file.path } ?: emptyList()
            filesLiveData.value = updatedList
        }
        return success
    }

    fun renameFile(oldFile: ModelFileItem, newFileName: String): Boolean {
        val oldFileObject = File(oldFile.path)
        val fileExtension = oldFileObject.extension
        val newFile = File(oldFileObject.parentFile, "$newFileName.$fileExtension")

        if (newFileName.contains(Regex("[\\\\/:*?\"<>|]"))) {
            return false
        }

        val success = oldFileObject.renameTo(newFile)

        if (success) {
            // Cập nhật danh sách file sau khi đổi tên
            val updatedList = filesLiveData.value?.map {
                if (it.path == oldFile.path) {
                    it.copy(name = newFile.name, path = newFile.absolutePath)
                } else {
                    it
                }
            } ?: emptyList()
            filesLiveData.value = updatedList
        }
        return success
    }


    // Thêm file vào yêu thích
    fun addFavoriteFile(file: ModelFileItem) {
        viewModelScope.launch {
            fileDao.addFavoriteFile(file)
        }
    }

    // Xóa file khỏi yêu thích
    fun removeFavoriteFile(file: ModelFileItem) {
        viewModelScope.launch {
            try {
                fileDao.removeFavoriteFile(file.path)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // Kiểm tra xem file có trong danh sách yêu thích hay không
    suspend fun isFileFavorite(filePath: String): Boolean {
        return fileDao.isFileFavorite(filePath) != null
    }



}