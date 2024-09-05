package com.example.pdfreader.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.pdfreader.model.ModelFileItem
import com.example.pdfreader.viewmodel.FileViewModel
import com.example.pdfreaderkotlin.databinding.FragmentDialogRenameFileBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File


class DialogRenameFile(
    private var file: ModelFileItem
) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentDialogRenameFileBinding
    private lateinit var fileViewModel: FileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogRenameFileBinding.inflate(LayoutInflater.from(requireContext()))
        initData()
        initView()
        initListener()
        return binding.root
    }

    private fun initData() {
        fileViewModel = ViewModelProvider(requireActivity())[FileViewModel::class.java]

    }

    private fun initView() {
        val nameWithoutExtension = File(file.path).nameWithoutExtension
        binding.edtFileName.setText(nameWithoutExtension)
    }

    private fun initListener() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnOk.setOnClickListener {
            val newFileName = binding.edtFileName.text.toString().trim()
            if (newFileName.isNotEmpty()) {
                // Gọi hàm đổi tên và giữ nguyên phần mở rộng
                val success = fileViewModel.renameFile(file, newFileName)
                if (success) {
                    Toast.makeText(requireContext(), "đổi tên thành công", Toast.LENGTH_SHORT).show()
                    dismiss()
                } else {
                    Toast.makeText(requireContext(), "không thể đổi tên", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "không thể để trống", Toast.LENGTH_SHORT).show()
            }
        }
    }


}