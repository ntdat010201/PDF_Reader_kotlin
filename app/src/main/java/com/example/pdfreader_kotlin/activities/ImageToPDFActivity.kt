package com.example.pdfreader_kotlin.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.pdfreader_kotlin.databinding.ActivityImageToPdfactivityBinding
import java.io.File

class ImageToPDFActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageToPdfactivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageToPdfactivityBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initData()
        initView()
        initListener()
    }

    private fun initData() {

    }

    private fun initView() {
        val file = File("/storage/emulated/0/Download/sample.pdf")
        if (file.exists()) {
            binding.imageToPdf.fromFile(file)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .load()
        }
    }

    private fun initListener() {

    }

}