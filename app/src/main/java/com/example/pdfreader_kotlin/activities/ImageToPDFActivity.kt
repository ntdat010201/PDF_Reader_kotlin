package com.example.pdfreader_kotlin.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.pdfreader_kotlin.databinding.ActivityImageToPdfactivityBinding

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

    }

    private fun initListener() {

    }

}