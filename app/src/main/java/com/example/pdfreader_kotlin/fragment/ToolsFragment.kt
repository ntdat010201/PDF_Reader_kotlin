package com.example.pdfreader_kotlin.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.pdfreader_kotlin.activities.ImageToPDFActivity
import com.example.pdfreader_kotlin.databinding.FragmentToolsBinding
import com.example.pdfreader_kotlin.utlis.Const.FILE_PICKER_REQUEST_CODE

class ToolsFragment : Fragment() {
    private lateinit var binding: FragmentToolsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentToolsBinding.inflate(LayoutInflater.from(requireContext()))
        initData()
        initView()
        initListener()
        return binding.root
    }

    private fun initData() {

    }

    private fun initView() {

    }


    private fun initListener() {
        binding.fileManager.setOnClickListener {
            openFileManager()
        }

        binding.imageToPdf.setOnClickListener {
            val intent = Intent(requireContext(), ImageToPDFActivity::class.java)
            startActivity(intent)
        }

        binding.importantMark.setOnClickListener {

        }

    }


    private fun openFileManager() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "*/download*" // Loại tệp mà bạn muốn chọn, ví dụ application/pdf cho tệp PDF
        startActivityForResult(intent, FILE_PICKER_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == AppCompatActivity.RESULT_OK) {
            data?.data?.let { uri ->
                handleFileUri(uri)
            }
        }
    }

    private fun handleFileUri(uri: Uri) {
        // Xử lý URI của tệp đã chọn, ví dụ: hiển thị đường dẫn
        Toast.makeText(requireContext(), "Tệp đã chọn: $uri", Toast.LENGTH_LONG).show()
    }


}