package com.application.wtf.di

import com.application.wtf.network.RetroInstance.Companion.provideOkHttpClient
import com.application.wtf.network.RetroInstance.Companion.provideRetrofit
import com.application.wtf.network.RetroService
import com.application.wtf.preference.SharePreference
import com.application.wtf.repository.MainCategoryRepository
import com.application.wtf.repository.UserRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit

fun repositoryModule() = module {

    factory { provideOkHttpClient() }
    single { provideRetrofit(get()) }

    single { UserRepository(get(), get()) }
    single { MainCategoryRepository(get()) }
    single { SharePreference(androidContext()) }
    single { get<Retrofit>().create(RetroService::class.java) }

}