package com.example.pdfreader.di

import com.example.pdfreader.activities.MainActivity
import com.example.pdfreader.viewmodel.FileViewModel
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