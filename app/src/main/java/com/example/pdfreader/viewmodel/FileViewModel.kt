package com.example.pdfreader.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pdfreader.model.ModelFileItem
import java.io.File

class FileViewModel : ViewModel() {

    private val filesLiveData = MutableLiveData<List<ModelFileItem>>()
    val fileItems: LiveData<List<ModelFileItem>> get() = filesLiveData

    private var currentSortOrder: String = "az"

//    private val itemChannel = Channel<ModelFileItem>(Channel.UNLIMITED)
//    init {
//        // Coroutine scope for continuous data fetching
//        viewModelScope.launch {
//            for (item in itemChannel) {
//                val currentList = filesLiveData.value?.toMutableList() ?: mutableListOf()
//                currentList.add(item)
//                filesLiveData.value = currentList
//            }
//        }
//    }


    fun loadFiles(directory: File) {
        if (directory.exists() && directory.isDirectory) {
            val fileItemsList = mutableListOf<ModelFileItem>()
            searchFiles(directory, fileItemsList)
            filesLiveData.value = fileItemsList
        } else {

        }
    }

    private fun searchFiles(directory: File, fileItemsList: MutableList<ModelFileItem>) {
        val fileExtensions = setOf("pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx")
        directory.listFiles()?.forEach { file ->
            if (file.isDirectory) {
                searchFiles(file, fileItemsList)
            } else if (file.extension in fileExtensions) {
                fileItemsList.add(
                    ModelFileItem(
                        file.name, file.absolutePath, file.name, file.lastModified(), file.length()
                    )
                )
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
        var oldFileObject = File(oldFile.path)
        var fileExtension = oldFileObject.extension
        var newFile = File(oldFileObject.parentFile, "$newFileName.$fileExtension")

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

}