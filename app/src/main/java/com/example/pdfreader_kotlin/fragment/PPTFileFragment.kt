package com.example.pdfreader_kotlin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pdfreader_kotlin.adapter.PPTFileAdapter
import com.example.pdfreader_kotlin.databinding.FragmentPPTFileBinding
import com.example.pdfreader_kotlin.dialog.DialogEditFile
import com.example.pdfreader_kotlin.dialog.DialogSortBy
import com.example.pdfreader_kotlin.model.ModelFileItem
import com.example.pdfreader_kotlin.viewmodel.FileViewModel

class PPTFileFragment : Fragment(), DialogSortBy.SortByListener {

    private lateinit var binding: FragmentPPTFileBinding
    private var pptFileAdapter: PPTFileAdapter? = null
//    private val fileViewModel by viewModel<FileViewModel>()

    private lateinit var fileViewModel : FileViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPPTFileBinding.inflate(LayoutInflater.from(requireContext()))
        initData()
        initView()
        initListener()
        return binding.root
    }

    private fun initData() {
        binding.rcvPptFile.layoutManager = LinearLayoutManager(requireContext())
        pptFileAdapter = PPTFileAdapter(emptyList())
        binding.rcvPptFile.adapter = pptFileAdapter

        fileViewModel = ViewModelProvider(requireActivity())[FileViewModel::class.java]

        fileViewModel.fileItems.observe(viewLifecycleOwner, Observer { files ->

            val pptFiles = mutableListOf<ModelFileItem>()
            files.forEach { file ->
                if (file.type.contains("ppt") || file.type.contains("pptx")) {
                    pptFiles.add(file)
                }
            }
            pptFileAdapter!!.updateFiles(pptFiles)
        })

    }

    private fun initView() {

    }

    private fun initListener() {
        pptFileAdapter?.onItemClick = { file ->
            val dialogEditFile = DialogEditFile(file)
            dialogEditFile.show(parentFragmentManager,dialogEditFile.tag)
        }
    }

    override fun onSortBySelected(sortBy: String) {
        fileViewModel.setSortOrder(sortBy)
    }

}