package com.application.wtf.network

import com.application.wtf.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {
    companion object {

        const val BASE_URL = "http://157.230.245.180:3001/"
        fun provideRetrofit(
            okHttpClient: OkHttpClient
        ): Retrofit =
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()

        fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
            val builder = OkHttpClient.Builder()
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(loggingInterceptor)
            builder.build()
        } else OkHttpClient
            .Builder()
            .addInterceptor(NetworkConnectionInterceptor())
            .build()
    }
}