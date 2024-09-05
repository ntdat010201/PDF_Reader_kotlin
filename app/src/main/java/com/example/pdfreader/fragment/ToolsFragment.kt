package com.example.pdfreader.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.pdfreaderkotlin.R
import com.example.pdfreaderkotlin.databinding.FragmentToolsBinding

class ToolsFragment : Fragment() {
private lateinit var binding : FragmentToolsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentToolsBinding.inflate(LayoutInflater.from(requireContext()))
        initData()
        initView()
        initListener()
        return inflater.inflate(R.layout.fragment_tools, container, false)
    }

    private fun initData() {

    }

    private fun initView() {

    }

    private fun initListener() {

    }

}