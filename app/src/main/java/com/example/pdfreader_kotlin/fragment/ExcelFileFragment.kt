package com.example.pdfreader_kotlin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pdfreader_kotlin.adapter.ExcelFileAdapter
import com.example.pdfreader_kotlin.databinding.FragmentExcelFileBinding
import com.example.pdfreader_kotlin.dialog.DialogEditFile
import com.example.pdfreader_kotlin.dialog.DialogSortBy
import com.example.pdfreader_kotlin.model.ModelFileItem
import com.example.pdfreader_kotlin.viewmodel.FileViewModel

class ExcelFileFragment : Fragment(), DialogSortBy.SortByListener {

    private lateinit var binding: FragmentExcelFileBinding

    private var excelFileAdapter: ExcelFileAdapter? = null
//    private val fileViewModel by viewModel<FileViewModel>()

    private lateinit var fileViewModel: FileViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentExcelFileBinding.inflate(LayoutInflater.from(requireContext()))
        initData()
        initView()
        initListener()
        return binding.root
    }

    private fun initData() {
        binding.rcvExcelFile.layoutManager = LinearLayoutManager(requireContext())
        excelFileAdapter = ExcelFileAdapter(emptyList())
        binding.rcvExcelFile.adapter = excelFileAdapter

        fileViewModel = ViewModelProvider(requireActivity())[FileViewModel::class.java]

        fileViewModel.fileItems.observe(viewLifecycleOwner, Observer { files ->
            val excelFile = mutableListOf<ModelFileItem>()

            files.forEach { file ->
                if (file.type.contains("xls") || file.type.contains("xlsx")) {
                    excelFile.add(file)
                }
            }
            excelFileAdapter!!.updateFiles(excelFile)
        })

    }

    private fun initView() {

    }

    private fun initListener() {
        excelFileAdapter?.onItemClick = { file ->
            val dialogEditFile = DialogEditFile(file)
            dialogEditFile.show(parentFragmentManager, dialogEditFile.tag)
        }
    }

    override fun onSortBySelected(sortBy: String) {
        fileViewModel.setSortOrder(sortBy)
    }

}