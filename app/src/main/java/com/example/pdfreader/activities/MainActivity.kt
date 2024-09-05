package com.example.pdfreader.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.example.pdfreader.adapter.ViewpagerActivityAdapter
import com.example.pdfreader.base.BaseActivity
import com.example.pdfreader.dialog.DialogSortBy
import com.example.pdfreader.fragment.AllFileFragment
import com.example.pdfreader.fragment.HomeFragment
import com.example.pdfreader.fragment.ToolsFragment
import com.example.pdfreader.utlis.Const.STORAGE_PERMISSION_CODE
import com.example.pdfreader.viewmodel.FileViewModel
import com.example.pdfreaderkotlin.R
import com.example.pdfreaderkotlin.databinding.ActivityMainBinding

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
        checkPermission()
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

        binding.settings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        binding.sortBy.setOnClickListener {
            val dialogSortBy = DialogSortBy()
            dialogSortBy.show(supportFragmentManager, dialogSortBy.tag)
        }

    }


    override fun onSortBySelected(sortBy: String) {
        // Find AllFileFragment and pass the sort order to it
        val allFileFragment = supportFragmentManager.findFragmentByTag("f0") as? AllFileFragment
        allFileFragment?.onSortBySelected(sortBy)
    }


    private fun checkPermission() {
        // Kiểm tra quyền đọc và ghi bộ nhớ ngoài
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(
                this, Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                STORAGE_PERMISSION_CODE
            )
        } else {

            fileViewModel.loadFiles(Environment.getExternalStorageDirectory())
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                // Quyền đã được cấp
                Toast.makeText(this, "Quyền đã được cấp", Toast.LENGTH_SHORT).show()
                fileViewModel.loadFiles(Environment.getExternalStorageDirectory())
            } else {
                // Quyền bị từ chối
                Toast.makeText(
                    this,
                    "Quyền bị từ chối, không thể truy cập file",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setColorTabLayout() {
        homeFragment.performAction = { position ->
            when (position) {
                0 -> {
                    binding.settings.setImageResource(R.drawable.ic_settings_black)
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
                    binding.settings.setImageResource(R.drawable.ic_settings_white)
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
                    binding.settings.setImageResource(R.drawable.ic_settings_white)
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
                    binding.settings.setImageResource(R.drawable.ic_settings_white)
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
                    binding.settings.setImageResource(R.drawable.ic_settings_white)
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