package com.example.pdfreader_kotlin.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pdfreader_kotlin.fragment.HomeFragment
import com.example.pdfreader_kotlin.fragment.ToolsFragment

class ViewpagerActivityAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    private var homeFragment: HomeFragment? = null
    private var toolsFragment: ToolsFragment? = null

    fun setFragments(
        homeFilesFragment: HomeFragment, toolsFragment: ToolsFragment
    ) {
        this.homeFragment = homeFilesFragment
        this.toolsFragment = toolsFragment
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> homeFragment!!
            1 -> toolsFragment!!
            else -> homeFragment!!
        }

    }
}