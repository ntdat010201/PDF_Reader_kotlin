package com.example.pdfreader_kotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pdfreader_kotlin.R
import com.example.pdfreader_kotlin.databinding.ItemFileBinding
import com.example.pdfreader_kotlin.model.ModelFileItem

class SearchFileAdapter(
    private var files: ArrayList<ModelFileItem>? = null
) : RecyclerView.Adapter<SearchFileAdapter.FileViewHolder>() {

    fun updateFiles(files: ArrayList<ModelFileItem>) {
        this.files = files
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        return FileViewHolder(
            ItemFileBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return files?.size ?: 0
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val fileItem = files?.get(position)
        if (fileItem != null) {
            holder.name.text = fileItem.name
            holder.iconFile.setImageResource(getFileIcon(fileItem.type))
        }

    }

    inner class FileViewHolder(binding: ItemFileBinding) : RecyclerView.ViewHolder(binding.root) {
        var name = binding.fileName
        var iconFile = binding.fileIcon
    }

    private fun getFileIcon(mimeType: String): Int {
        return when {
            mimeType.contains("pdf") -> R.drawable.ic_pdf
            mimeType.contains("doc") || mimeType.contains("docx") -> R.drawable.ic_doc
            mimeType.contains("xls") || mimeType.contains("xlsx") -> R.drawable.ic_xls
            mimeType.contains("ppt") || mimeType.contains("pptx") -> R.drawable.ic_ppt
            else -> R.drawable.ic_pdf // Hình ảnh mặc định nếu không xác định được loại file
        }
    }
}