package com.example.pdfreader_kotlin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pdfreader_kotlin.adapter.PDFFileAdapter
import com.example.pdfreader_kotlin.databinding.FragmentPDFFileBinding
import com.example.pdfreader_kotlin.dialog.DialogEditFile
import com.example.pdfreader_kotlin.dialog.DialogSortBy
import com.example.pdfreader_kotlin.model.ModelFileItem
import com.example.pdfreader_kotlin.viewmodel.FileViewModel


class PDFFileFragment : Fragment() , DialogSortBy.SortByListener{
    private lateinit var binding: FragmentPDFFileBinding

    private var pdfFileAdapter: PDFFileAdapter? = null

    private lateinit var fileViewModel : FileViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentPDFFileBinding.inflate(LayoutInflater.from(requireContext()))
        initData()
        initView()
        initListener()
        return binding.root
    }

    private fun initData() {

        binding.rcvPdfFile.layoutManager = LinearLayoutManager(requireContext())
        pdfFileAdapter = PDFFileAdapter(emptyList())
        binding.rcvPdfFile.adapter = pdfFileAdapter

        fileViewModel = ViewModelProvider(requireActivity())[FileViewModel::class.java]

        fileViewModel.fileItems.observe(viewLifecycleOwner, Observer { files ->

            val filesPdf = mutableListOf<ModelFileItem>()

            files.forEach { file ->
                if (file.type.contains("pdf")) {
                    filesPdf.add(file)
                }
            }

            pdfFileAdapter!!.updateFiles(filesPdf)

        })

    }

    private fun initView() {

    }

    private fun initListener() {
        pdfFileAdapter?.onItemClick = { file ->
            val dialogEditFile = DialogEditFile(file)
            dialogEditFile.show(parentFragmentManager,dialogEditFile.tag)
        }
    }

    override fun onSortBySelected(sortBy: String) {
        fileViewModel.setSortOrder(sortBy)
    }


}