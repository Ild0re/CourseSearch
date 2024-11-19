package com.innerproject.coursesearch.domain.home

import com.innerproject.coursesearch.domain.models.Course
import com.innerproject.coursesearch.domain.models.Summary
import kotlinx.coroutines.flow.Flow

interface HomeInteractor {
    suspend fun getCourses(number: Int): Flow<Pair<List<Course>?, String?>>

    suspend fun getAllStars(): Flow<Pair<List<Summary>?, String?>>
}