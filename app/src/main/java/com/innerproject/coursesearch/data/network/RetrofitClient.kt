package com.innerproject.coursesearch.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val client: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://stepik.org")
            .addConverterFactory(
                GsonConverterFactory
                    .create()
            )
            .build()
    }

    val api: CourseAPI by lazy {
        client.create(CourseAPI::class.java)
    }
}