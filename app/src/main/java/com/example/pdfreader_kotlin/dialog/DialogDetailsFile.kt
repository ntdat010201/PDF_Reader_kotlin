package com.example.pdfreader_kotlin.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pdfreader_kotlin.databinding.FragmentDialogDetailsFileBinding
import com.example.pdfreader_kotlin.model.ModelFileItem
import com.example.pdfreader_kotlin.utlis.FormatUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogDetailsFile(
    private var file: ModelFileItem
) : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentDialogDetailsFileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogDetailsFileBinding.inflate(LayoutInflater.from(requireContext()))
        initData()
        initView()
        initVieListener()
        return binding.root
    }

    private fun initData() {

    }

    private fun initView() {
        binding.fileName.text = file.name
        binding.storagePath.text = file.path
        binding.lastModified.text = FormatUtil.formatFileDate(file.lastModified)
        binding.fileSize.text = FormatUtil.formatFileSize(file.size)
    }

    private fun initVieListener() {
        binding.btnOk.setOnClickListener {
            dismiss()
        }
    }


}