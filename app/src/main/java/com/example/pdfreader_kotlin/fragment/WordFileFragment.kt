package com.example.pdfreader_kotlin.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pdfreader_kotlin.activities.OpenFileDocXlsPptActivity
import com.example.pdfreader_kotlin.adapter.WordFileAdapter
import com.example.pdfreader_kotlin.databinding.FragmentWordFileBinding
import com.example.pdfreader_kotlin.dialog.DialogEditFile
import com.example.pdfreader_kotlin.dialog.DialogSortBy
import com.example.pdfreader_kotlin.model.ModelFileItem
import com.example.pdfreader_kotlin.viewmodel.FileViewModel


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
        wordFileAdapter?.onItemClickMore = { file ->
            val dialogEditFile = DialogEditFile(file)
            dialogEditFile.show(parentFragmentManager,dialogEditFile.tag)
        }

        wordFileAdapter?.onItemClickItem = {file ->
            val intent = Intent(requireContext(), OpenFileDocXlsPptActivity::class.java)
            intent.putExtra("data",file)
            startActivity(intent)
        }
    }

    override fun onSortBySelected(sortBy: String) {
        fileViewModel.setSortOrder(sortBy)
    }


}