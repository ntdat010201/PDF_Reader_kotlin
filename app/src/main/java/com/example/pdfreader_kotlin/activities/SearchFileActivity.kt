package com.example.pdfreader_kotlin.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pdfreader_kotlin.adapter.SearchFileAdapter
import com.example.pdfreader_kotlin.databinding.ActivitySearchFileBinding
import com.example.pdfreader_kotlin.model.ModelFileItem

class SearchFileActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchFileBinding

    private var searchFileAdapter: SearchFileAdapter? = null
//    private lateinit var fileViewModel: FileViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchFileBinding.inflate(LayoutInflater.from(this))
        initData()
        initView()
        initListener()
        setContentView(binding.root)
    }

    private fun initData() {
        val files: ArrayList<ModelFileItem>? = intent.getParcelableArrayListExtra("data")
        binding.rcvSearchFile.layoutManager = LinearLayoutManager(this)
        searchFileAdapter = SearchFileAdapter()
        binding.rcvSearchFile.adapter = searchFileAdapter

        if (files != null) {
            searchFile(files)
        }
    }

    private fun initView() {

        binding.searchView.isIconified = false
        binding.searchView.requestFocus()
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.searchView, InputMethodManager.SHOW_IMPLICIT)

        binding.searchView.setOnQueryTextFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val im = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                im.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            }
        }
    }

    private fun initListener() {
        binding.backBtn.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }

    private fun searchFile(files: ArrayList<ModelFileItem>) {

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false


            override fun onQueryTextChange(newText: String?): Boolean {
                val mList = ArrayList<ModelFileItem>()

                if (!newText.isNullOrEmpty()) {
                    val userInput = newText.lowercase()
                    for (file in files) {
                        if (file.name.lowercase().contains(userInput)) {
                            mList.add(file)
                        }
                    }
                }

                searchFileAdapter?.updateFiles(mList)

                if (mList.isEmpty()) {
                    binding.rcvSearchFile.visibility = View.GONE
                } else {
                    binding.rcvSearchFile.visibility = View.VISIBLE
                }

                return true
            }


        })
    }


}