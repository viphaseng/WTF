package com.application.wtf

import android.app.Application
import com.application.wtf.di.multiModule
import com.application.wtf.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

open class App : Application() {

    override fun onCreate() {
        super.onCreate()
        configureDi()
    }

    // CONFIGURATION ---
    open fun configureDi() =
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    multiModule,
                    repositoryModule()

                )
            )
        }
}