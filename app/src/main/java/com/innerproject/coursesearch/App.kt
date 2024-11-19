package com.innerproject.coursesearch

import android.app.Application
import com.innerproject.coursesearch.di.dataModule
import com.innerproject.coursesearch.di.interactorModule
import com.innerproject.coursesearch.di.repositoryModule
import com.innerproject.coursesearch.di.viewModelModule
import com.jakewharton.threetenabp.AndroidThreeTen
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(dataModule, repositoryModule, interactorModule, viewModelModule)
        }

        AndroidThreeTen.init(this)
    }
}