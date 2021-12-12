package com.application.wtf.di

import com.application.wtf.viewmodel.AuthViewModel
import com.application.wtf.viewmodel.CategoryViewModel
import com.application.wtf.viewmodel.OrderViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val multiModule = module {
    viewModel { AuthViewModel(get()) }
    viewModel { CategoryViewModel(get()) }
    viewModel { OrderViewModel(get(), get()) }
}