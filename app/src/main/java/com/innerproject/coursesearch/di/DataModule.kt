package com.innerproject.coursesearch.di

import androidx.room.Room
import com.google.gson.Gson
import com.innerproject.coursesearch.data.db.AppDatabase
import com.innerproject.coursesearch.data.network.CourseAPI
import com.innerproject.coursesearch.data.network.NetworkClient
import com.innerproject.coursesearch.data.network.RetrofitNetworkClient
import com.innerproject.coursesearch.domain.models.Course
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {
    single<CourseAPI> {
        Retrofit.Builder()
            .baseUrl("https://stepik.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CourseAPI::class.java)
    }

    factory { Gson() }

    single<NetworkClient> {
        RetrofitNetworkClient(androidContext())
    }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "database.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}