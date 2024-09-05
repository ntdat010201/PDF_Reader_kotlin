package com.example.pdfreader.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.pdfreader.model.ModelFileItem
import com.example.pdfreader.viewmodel.FileViewModel
import com.example.pdfreaderkotlin.databinding.FragmentDialogDeleteFileBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogDeleteFile(
    private var file: ModelFileItem
) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentDialogDeleteFileBinding
    private lateinit var fileViewModel: FileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogDeleteFileBinding.inflate(LayoutInflater.from(requireContext()))
        initData()
        initView()
        initListener()
        return binding.root
    }

    private fun initData() {
        fileViewModel = ViewModelProvider(requireActivity())[FileViewModel::class.java]
    }

    private fun initView() {
        binding.fileName.text = file.name
        binding.filePath.text = file.path
    }

    private fun initListener() {
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        binding.btnOk.setOnClickListener {
            val success = fileViewModel.deleteFile(file)
            if (success) {
                Toast.makeText(requireContext(), "xóa thành công ", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "không thể xóa file ", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }


}