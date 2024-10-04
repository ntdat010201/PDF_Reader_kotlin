package com.example.pdfreader_kotlin.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pdfreader_kotlin.activities.OpenFileDocXlsPptActivity
import com.example.pdfreader_kotlin.activities.OpenFilePdfActivity
import com.example.pdfreader_kotlin.adapter.AllFileAdapter
import com.example.pdfreader_kotlin.databinding.FragmentAllFileBinding
import com.example.pdfreader_kotlin.dialog.DialogEditFile
import com.example.pdfreader_kotlin.dialog.DialogSortBy
import com.example.pdfreader_kotlin.viewmodel.FileViewModel
import kotlin.math.log

class AllFileFragment : Fragment() , DialogSortBy.SortByListener {
    private lateinit var binding: FragmentAllFileBinding
    private var allFileAdapter: AllFileAdapter? = null

    //    private val fileViewModel by viewModel<FileViewModel>()
    private lateinit var fileViewModel: FileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentAllFileBinding.inflate(LayoutInflater.from(requireContext()))
        initData()
        initView()
        initListener()
        return binding.root
    }

    private fun initData() {

        binding.rcvAllFile.layoutManager = LinearLayoutManager(requireContext())
        allFileAdapter = AllFileAdapter(emptyList())
        binding.rcvAllFile.adapter = allFileAdapter

        fileViewModel = ViewModelProvider(requireActivity())[FileViewModel::class.java]
        fileViewModel.fileItems.observe(viewLifecycleOwner, Observer { files ->
            allFileAdapter?.updateFiles(files)
        })

    }

    private fun initView() {

    }

    private fun initListener() {
        allFileAdapter?.onItemClickMore = { file ->
            val dialogEditFile = DialogEditFile(file)
            dialogEditFile.show(parentFragmentManager,dialogEditFile.tag)
        }

        allFileAdapter?.onItemClickItem = { file ->

            if (file.type.contains("pdf")){
                val intent = Intent(requireContext(), OpenFilePdfActivity::class.java)
                intent.putExtra("data_pdf",file)
                startActivity(intent)
            } else {
                val intent = Intent(requireContext(), OpenFileDocXlsPptActivity::class.java)
                intent.putExtra("data",file)
                startActivity(intent)
            }
        }

    }

    override fun onSortBySelected(sortBy: String) {
        fileViewModel.setSortOrder(sortBy)
    }


}