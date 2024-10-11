package com.example.pdfreader_kotlin.activities

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.example.pdfreader_kotlin.databinding.ActivityImportantFolderBinding

class ImportantFolderActivity : AppCompatActivity() {
    private lateinit var binding : ActivityImportantFolderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImportantFolderBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

    }
}