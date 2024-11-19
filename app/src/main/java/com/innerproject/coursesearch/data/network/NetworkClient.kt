package com.innerproject.coursesearch.data.network

import com.innerproject.coursesearch.data.dto.Response

interface NetworkClient {
    suspend fun getCourses(number: Int): Response

    suspend fun getAllStars(): Response

    suspend fun getStarFromCourse(id: Int): Response
}