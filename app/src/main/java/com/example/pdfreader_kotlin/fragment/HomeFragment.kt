package com.example.pdfreader_kotlin.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.fragment.app.Fragment
import com.example.pdfreader_kotlin.R
import com.example.pdfreader_kotlin.adapter.ViewPagerHomeAdapter
import com.example.pdfreader_kotlin.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    private val allFileFragment = AllFileFragment()
    private val pdfFileFragment = PDFFileFragment()
    private val wordFileFragment = WordFileFragment()
    private val excelFileFragment = ExcelFileFragment()
    private val pptFileFragment = PPTFileFragment()

    private var adapter: ViewPagerHomeAdapter? = null
    var performAction: ((Int) -> Unit)? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(LayoutInflater.from(requireContext()))
        initData()
        initView()
        initListener()
        return binding.root
    }


    private fun initData() {

        setColorTabs()

        adapter = ViewPagerHomeAdapter(requireActivity())
        adapter!!.setFragments(
            allFileFragment, pdfFileFragment, wordFileFragment, excelFileFragment, pptFileFragment
        )
        binding.viewPager2.adapter = adapter
        binding.viewPager2.offscreenPageLimit = 5   // Thiết lập số trang giữ trong bộ nhớ
        TabLayoutMediator(binding.tabLayout, binding.viewPager2) { tab, position ->
            tab.text = when (position) {
                0 -> "All";
                1 -> "PDF"
                2 -> "Word"
                3 -> "Excel"
                else -> "PPT"
            }

        }.attach()

    }

    private fun initView() {
    }

    private fun initListener() {

    }

    private fun setSystemBarColors(navigationBarColorResId: Int, statusBarColorResId: Int) {
        activity?.window?.let { window ->
            // Thay đổi màu của navigation bar
            window.navigationBarColor =
                ContextCompat.getColor(requireContext(), navigationBarColorResId)
            // Thay đổi màu của status bar
            window.statusBarColor = ContextCompat.getColor(requireContext(), statusBarColorResId)
        }
    }

     fun setColorTabs() {

        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 -> {
                        WindowCompat.getInsetsController(requireActivity().window,requireActivity().window.decorView).isAppearanceLightStatusBars = true

                        binding.tabLayout.setTabTextColors(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.gray_hint
                            ), // Màu chữ không được chọn
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.standard_black
                            ) // Màu chữ được chọn
                        )
                        performAction?.invoke(tab.position)
                        setSystemBarColors(R.color.white, R.color.white)
                        binding.tabLayout.setBackgroundResource(R.color.white)
                    }

                    1 -> {

                        binding.tabLayout.setTabTextColors(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.light_grey
                            ),
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white
                            )
                        )
                        performAction?.invoke(tab.position)
                        setSystemBarColors(R.color.medium_red, R.color.medium_red)
                        binding.tabLayout.setBackgroundResource(R.color.medium_red)
                    }

                    2 -> {
                        binding.tabLayout.setTabTextColors(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.light_grey
                            ),
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white
                            )
                        )
                        performAction?.invoke(tab.position)
                        setSystemBarColors(R.color.medium_blue, R.color.medium_blue)
                        binding.tabLayout.setBackgroundResource(R.color.medium_blue)
                    }

                    3 -> {
                        binding.tabLayout.setTabTextColors(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.light_grey
                            ),
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white
                            )
                        )
                        performAction?.invoke(tab.position)
                        setSystemBarColors(R.color.medium_green, R.color.medium_green)
                        binding.tabLayout.setBackgroundResource(R.color.medium_green)
                    }

                    else -> {
                        binding.tabLayout.setTabTextColors(
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.light_grey
                            ),
                            ContextCompat.getColor(
                                requireContext(),
                                R.color.white
                            )
                        )
                        performAction?.invoke(tab.position)
                        setSystemBarColors(R.color.medium_orange, R.color.medium_orange)
                        binding.tabLayout.setBackgroundResource(R.color.medium_orange)
                    }

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }


}