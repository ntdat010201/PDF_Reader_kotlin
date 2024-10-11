package com.example.pdfreader_kotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pdfreader_kotlin.databinding.ItemFileBinding
import com.example.pdfreader_kotlin.model.ModelFileItem
import com.example.pdfreader_kotlin.utlis.FileIconUtil
import com.example.pdfreader_kotlin.utlis.FormatUtil

class SearchFileAdapter(
    private var files: ArrayList<ModelFileItem>? = null
) : RecyclerView.Adapter<SearchFileAdapter.FileViewHolder>() {
    var onItemClickMore: ((ModelFileItem) -> Unit)? = null
    var onItemClickItem: ((ModelFileItem) -> Unit)? = null
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
        val fileItem = files!![position]

        holder.nameFile.text = fileItem.name
        holder.iconFile.setImageResource(FileIconUtil.getFileIcon(fileItem.type))
        holder.lastModified.text = FormatUtil.formatFileDate(fileItem.lastModified)
        holder.sizeFile.text = FormatUtil.formatFileSize(fileItem.size)

        holder.more.setOnClickListener {
            onItemClickMore?.invoke(fileItem)
        }

        holder.itemView.setOnClickListener {
            onItemClickItem?.invoke(fileItem)
        }
    }

    inner class FileViewHolder(binding: ItemFileBinding) : RecyclerView.ViewHolder(binding.root) {
        val nameFile = binding.fileName
        val iconFile = binding.fileIcon
        val lastModified = binding.lastModified
        val sizeFile = binding.sizeFile
        val more = binding.moreVert
    }

}