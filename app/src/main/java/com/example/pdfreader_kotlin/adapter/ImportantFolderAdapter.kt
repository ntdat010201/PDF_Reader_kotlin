package com.example.pdfreader_kotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pdfreader_kotlin.databinding.ItemFileBinding
import com.example.pdfreader_kotlin.model.ModelFileItem
import com.example.pdfreader_kotlin.utlis.FileIconUtil
import com.example.pdfreader_kotlin.utlis.FormatUtil

class ImportantFolderAdapter(
    private var files : List<ModelFileItem>
) : RecyclerView.Adapter<ImportantFolderAdapter.FileViewHolder>(){

    fun updateFiles(files: List<ModelFileItem>) {
        this.files = files
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        return FileViewHolder(ItemFileBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = files.size

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val fileItem = files[position]

        holder.nameFile.text = fileItem.name
        holder.iconFile.setImageResource(FileIconUtil.getFileIcon(fileItem.type))
        holder.lastModified.text = FormatUtil.formatFileDate(fileItem.lastModified)
        holder.sizeFile.text = FormatUtil.formatFileSize(fileItem.size)
    }


    inner class FileViewHolder(binding : ItemFileBinding) : RecyclerView.ViewHolder(binding.root){
        val nameFile = binding.fileName
        val iconFile = binding.fileIcon
        val lastModified = binding.lastModified
        val sizeFile = binding.sizeFile
        val more = binding.moreVert
    }


}