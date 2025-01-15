package com.mhmtn.DishWhiz

import android.app.Application
import com.mhmtn.DishWhiz.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class DishWhizApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DishWhizApp)
            modules(appModule)
        }
    }
}