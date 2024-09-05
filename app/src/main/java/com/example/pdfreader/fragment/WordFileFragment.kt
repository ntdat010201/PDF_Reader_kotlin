package com.example.pdfreader.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pdfreader.adapter.WordFileAdapter
import com.example.pdfreader.dialog.DialogEditFile
import com.example.pdfreader.dialog.DialogSortBy
import com.example.pdfreader.model.ModelFileItem
import com.example.pdfreader.viewmodel.FileViewModel
import com.example.pdfreaderkotlin.databinding.FragmentWordFileBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class WordFileFragment : Fragment(), DialogSortBy.SortByListener {

    private lateinit var binding: FragmentWordFileBinding

    private var wordFileAdapter: WordFileAdapter? = null

    //    private val fileViewModel by viewModel<FileViewModel>()
    private lateinit var fileViewModel: FileViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentWordFileBinding.inflate(LayoutInflater.from(requireContext()))
        initData()
        initView()
        initListener()
        return binding.root
    }

    private fun initData() {
        binding.rcvDocFile.layoutManager = LinearLayoutManager(requireContext())
        wordFileAdapter = WordFileAdapter(emptyList())
        binding.rcvDocFile.adapter = wordFileAdapter

        fileViewModel = ViewModelProvider(requireActivity())[FileViewModel::class.java]

        fileViewModel.fileItems.observe(viewLifecycleOwner) { files ->
            val filesDoc = mutableListOf<ModelFileItem>()

            files.forEach { file ->
                if (file.type.contains("doc") || file.type.contains("docx")) {
                    filesDoc.add(file)
                }
            }
            wordFileAdapter!!.updateFile(filesDoc)

        }

    }

    private fun initView() {

    }

    private fun initListener() {
        wordFileAdapter?.onItemClick = { file ->
            val dialogEditFile = DialogEditFile(file)
            dialogEditFile.show(parentFragmentManager,dialogEditFile.tag)
        }
    }

    override fun onSortBySelected(sortBy: String) {
        fileViewModel.setSortOrder(sortBy)
    }


}