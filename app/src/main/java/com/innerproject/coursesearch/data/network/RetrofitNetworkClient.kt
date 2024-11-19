package com.innerproject.coursesearch.data.network

import android.content.Context
import com.innerproject.coursesearch.data.dto.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RetrofitNetworkClient(private val context: Context): NetworkClient {
    override suspend fun getCourses(number: Int): Response {
        return getResponse(RetrofitClient.api.getCourses(number))
    }

    override suspend fun getAllStars(): Response {
        return getResponse(RetrofitClient.api.getAllStars())
    }

    override suspend fun getStarFromCourse(id: Int): Response {
        return getResponse(RetrofitClient.api.getStarFromCourse(id))
    }

    private suspend fun getResponse(nextResponse: Response): Response {
        try {
            return withContext(Dispatchers.IO) {
                try{
                    val response = nextResponse
                    response.apply { resultCode = 200 }
                } catch (e: Throwable) {
                    Response().apply { resultCode = 500 }
                }
            }
        } catch (ex: Exception) {
            return Response().apply { resultCode = 400 }
        }
    }
}