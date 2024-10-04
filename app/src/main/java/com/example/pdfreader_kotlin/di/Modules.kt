package com.example.pdfreader_kotlin.di

import com.example.pdfreader_kotlin.activities.MainActivity
import com.example.pdfreader_kotlin.viewmodel.FileViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainActivity = module {
    scope<MainActivity> {
        viewModel { FileViewModel() }

    }
//    fragment { ToolsFragment() }
//    fragment { HomeFragment() }

}

val listModule = listOf(
    mainActivity
)