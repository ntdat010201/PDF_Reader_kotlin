package com.example.pdfreader_kotlin.di

import org.koin.dsl.module

val mainActivity = module {
//    scope<MainActivity> {
////        viewModel { FileViewModel(androidApplication()) }
//
//        viewModel { (application: Application) -> FileViewModel(application) }
//
//    }
//    fragment { ToolsFragment() }
//    fragment { HomeFragment() }

}

val listModule = listOf(
    mainActivity
)