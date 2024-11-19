package com.innerproject.coursesearch.data.home

import com.innerproject.coursesearch.domain.models.Course
import com.innerproject.coursesearch.domain.models.Summary
import com.innerproject.coursesearch.utils.Resource
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    suspend fun getCourses(number: Int): Flow<Resource<List<Course>>>

    suspend fun getAllStars(): Flow<Resource<List<Summary>>>
}