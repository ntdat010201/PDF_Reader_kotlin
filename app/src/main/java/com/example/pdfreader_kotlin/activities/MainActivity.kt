package com.example.pdfreader_kotlin.activities

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.pdfreader_kotlin.R
import com.example.pdfreader_kotlin.adapter.ViewpagerActivityAdapter
import com.example.pdfreader_kotlin.base.BaseActivity
import com.example.pdfreader_kotlin.databinding.ActivityMainBinding
import com.example.pdfreader_kotlin.dialog.DialogSortBy
import com.example.pdfreader_kotlin.fragment.AllFileFragment
import com.example.pdfreader_kotlin.fragment.HomeFragment
import com.example.pdfreader_kotlin.fragment.ToolsFragment
import com.example.pdfreader_kotlin.viewmodel.FileViewModel


class MainActivity : BaseActivity(), DialogSortBy.SortByListener {

    private lateinit var binding: ActivityMainBinding
    private val homeFragment = HomeFragment()
    private val toolsFragment = ToolsFragment()
    private var adapter: ViewpagerActivityAdapter? = null
    private lateinit var fileViewModel: FileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        initData()
        fileViewModel = ViewModelProvider(this)[FileViewModel::class.java]
        initView()
        initListener()
//        checkPermission()
        loadData()
    }

    private fun initData() {

        adapter = ViewpagerActivityAdapter(this)
        adapter!!.setFragments(homeFragment, toolsFragment)

        binding.viewPager2.adapter = adapter
        binding.viewPager2.offscreenPageLimit = 2   // Thiết lập số trang giữ trong bộ nhớ
        binding.viewPager2.isUserInputEnabled = false   // khóa cuộn swipe

        binding.viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> {
                        binding.bottomNavigation.menu.findItem(R.id.menu_home).isChecked = true
                    }

                    else -> binding.bottomNavigation.menu.findItem(R.id.menu_tools).isChecked = true
                }
            }
        })

        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.menu_home -> {
                    binding.viewPager2.currentItem = 0
                    binding.sortBy.visibility = View.VISIBLE
                    binding.searchFile.visibility = View.VISIBLE
                    true
                }

                R.id.menu_tools -> {
                    binding.viewPager2.currentItem = 1
                    binding.sortBy.visibility = View.INVISIBLE
                    binding.searchFile.visibility = View.INVISIBLE
                    true
                }

                else -> false
            }
        }

    }




    private fun initView() {
        setColorTabLayout()
    }


    private fun initListener() {
        fileViewModel.fileItems.observe(this, Observer { files ->
            binding.searchFile.setOnClickListener {
                val intent = Intent(this, SearchFileActivity::class.java)
                intent.putParcelableArrayListExtra("data", ArrayList(files))
                startActivity(intent)
            }
        })


        binding.sortBy.setOnClickListener {
            val dialogSortBy = DialogSortBy()
            dialogSortBy.show(supportFragmentManager, dialogSortBy.tag)
        }

    }

    private fun loadData(){
        fileViewModel.loadFiles(Environment.getExternalStorageDirectory())
    }

    override fun onSortBySelected(sortBy: String) {
        // Find AllFileFragment and pass the sort order to it
        val allFileFragment = supportFragmentManager.findFragmentByTag("f0") as? AllFileFragment
        allFileFragment?.onSortBySelected(sortBy)
    }




    private fun setColorTabLayout() {
        homeFragment.performAction = { position ->
            when (position) {
                0 -> {
                    binding.sortBy.setImageResource(R.drawable.ic_sort_black)
                    binding.searchFile.setImageResource(R.drawable.ic_search_black)

                    binding.nameFile.setTextColor(
                        ContextCompat.getColor(
                            this,
                            R.color.standard_black
                        )
                    )
                    binding.constraintLayout.setBackgroundResource(R.color.white)
                    WindowCompat.getInsetsController(
                        window,
                        window.decorView
                    ).isAppearanceLightStatusBars = true
                }

                1 -> {
                    binding.sortBy.setImageResource(R.drawable.ic_sort_white)
                    binding.searchFile.setImageResource(R.drawable.ic_search_white)

                    binding.nameFile.setTextColor(ContextCompat.getColor(this, R.color.white))
                    binding.constraintLayout.setBackgroundResource(R.color.medium_red)
                    WindowCompat.getInsetsController(
                        window,
                        window.decorView
                    ).isAppearanceLightStatusBars = false
                }

                2 -> {
                    binding.sortBy.setImageResource(R.drawable.ic_sort_white)
                    binding.searchFile.setImageResource(R.drawable.ic_search_white)

                    binding.nameFile.setTextColor(ContextCompat.getColor(this, R.color.white))
                    binding.constraintLayout.setBackgroundResource(R.color.medium_blue)
                    WindowCompat.getInsetsController(
                        window,
                        window.decorView
                    ).isAppearanceLightStatusBars = false
                }


                3 -> {
                    binding.sortBy.setImageResource(R.drawable.ic_sort_white)
                    binding.searchFile.setImageResource(R.drawable.ic_search_white)

                    binding.nameFile.setTextColor(ContextCompat.getColor(this, R.color.white))
                    binding.constraintLayout.setBackgroundResource(R.color.medium_green)
                    WindowCompat.getInsetsController(
                        window,
                        window.decorView
                    ).isAppearanceLightStatusBars = false
                }

                else -> {
                    binding.sortBy.setImageResource(R.drawable.ic_sort_white)
                    binding.searchFile.setImageResource(R.drawable.ic_search_white)

                    binding.nameFile.setTextColor(ContextCompat.getColor(this, R.color.white))
                    binding.constraintLayout.setBackgroundResource(R.color.medium_orange)
                    WindowCompat.getInsetsController(
                        window,
                        window.decorView
                    ).isAppearanceLightStatusBars = false
                }
            }
        }
    }

}