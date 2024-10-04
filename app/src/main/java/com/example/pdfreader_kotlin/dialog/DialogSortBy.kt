package com.example.pdfreader_kotlin.dialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.pdfreader_kotlin.databinding.FragmentDialogSortByBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DialogSortBy : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentDialogSortByBinding

    interface SortByListener {
        fun onSortBySelected(sortBy: String)
    }

    private var listener: SortByListener? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogSortByBinding.inflate(LayoutInflater.from(requireContext()))
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
        binding.az.setOnClickListener {
            listener?.onSortBySelected("az")
            dismiss()
        }

        binding.za.setOnClickListener {
            listener?.onSortBySelected("za")
            dismiss()
        }

        binding.newToOld.setOnClickListener {
            listener?.onSortBySelected("nto")
            dismiss()
        }

         binding.oldToNew.setOnClickListener {
            listener?.onSortBySelected("otn")
            dismiss()
        }

         binding.sizeB.setOnClickListener {
            listener?.onSortBySelected("bts")
            dismiss()
        }

         binding.sizeS.setOnClickListener {
            listener?.onSortBySelected("stb")
            dismiss()
        }


    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = try {
            context as SortByListener
        } catch (e: ClassCastException) {
            throw ClassCastException("$context must implement SortByListener")
        }
    }


}