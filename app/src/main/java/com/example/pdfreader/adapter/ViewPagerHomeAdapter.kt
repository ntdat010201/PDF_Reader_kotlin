package com.example.pdfreader.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.pdfreader.fragment.*

class ViewPagerHomeAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    private var allFileFragment: AllFileFragment? = null
    private var pdfFileFragment: PDFFileFragment? = null
    private var wordFileFragment: WordFileFragment? = null
    private var excelFileFragment: ExcelFileFragment? = null
    private var pptFileFragment: PPTFileFragment? = null

    fun setFragments(
        allFileFragment: AllFileFragment,
        pdfFileFragment: PDFFileFragment,
        wordFileFragment: WordFileFragment,
        excelFileFragment: ExcelFileFragment,
        pptFileFragment: PPTFileFragment
    ) {
        this.allFileFragment = allFileFragment
        this.pdfFileFragment = pdfFileFragment
        this.wordFileFragment = wordFileFragment
        this.excelFileFragment = excelFileFragment
        this.pptFileFragment = pptFileFragment
    }

    override fun getItemCount(): Int {
        return 5
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> allFileFragment!!
            1 -> pdfFileFragment!!
            2 -> wordFileFragment!!
            3 -> excelFileFragment!!
            4 -> pptFileFragment!!
            else -> allFileFragment!!
        }

    }


}