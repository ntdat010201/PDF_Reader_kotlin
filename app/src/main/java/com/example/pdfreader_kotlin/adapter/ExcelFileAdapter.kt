package com.example.pdfreader_kotlin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pdfreader_kotlin.R
import com.example.pdfreader_kotlin.databinding.ItemFileBinding
import com.example.pdfreader_kotlin.model.ModelFileItem
import com.example.pdfreader_kotlin.utlis.FormatUtil

class ExcelFileAdapter(
    private var files : List<ModelFileItem>
) : RecyclerView.Adapter<ExcelFileAdapter.FileViewHolder>(){
    var onItemClick: ((ModelFileItem) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        return FileViewHolder(ItemFileBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return files.size
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        val fileItem = files[position]

        holder.name.text = fileItem.name
        holder.iconFile.setImageResource(R.drawable.ic_xls)
        holder.lastModified.text = FormatUtil.formatFileDate(fileItem.lastModified)
        holder.sizeFile.text = FormatUtil.formatFileSize(fileItem.size)

        holder.more.setOnClickListener {
            onItemClick?.invoke(fileItem)
        }
    }

    fun updateFiles(files : List<ModelFileItem>){
        this.files = files
        notifyDataSetChanged()
    }

    inner class FileViewHolder(binding : ItemFileBinding) : RecyclerView.ViewHolder(binding.root) {
        var name = binding.fileName
        var iconFile = binding.fileIcon
        val lastModified = binding.lastModified
        val sizeFile = binding.sizeFile
        val more = binding.moreVert

    }
}