package com.example.pdfreader_kotlin.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pdfreader_kotlin.adapter.ImportantFolderAdapter
import com.example.pdfreader_kotlin.databinding.ActivityImportantFolderBinding
import com.example.pdfreader_kotlin.viewmodel.FileViewModel

class ImportantFolderActivity : AppCompatActivity() {
    private lateinit var binding : ActivityImportantFolderBinding
    private var importantFolderAdapter : ImportantFolderAdapter? = null

    private lateinit var fileViewModel: FileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImportantFolderBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initData()
        initView()
        initListener()
    }

    private fun initData() {
        fileViewModel = ViewModelProvider(this)[FileViewModel::class.java]

        binding.rcvImportantFolder.layoutManager = LinearLayoutManager(this)
        importantFolderAdapter = ImportantFolderAdapter(emptyList())
        binding.rcvImportantFolder.adapter = importantFolderAdapter

        fileViewModel.favoriteFiles.observe(this) { files ->
            importantFolderAdapter?.updateFiles(files)
        }

    }

    private fun initView() {

    }

    private fun initListener() {

    }
}