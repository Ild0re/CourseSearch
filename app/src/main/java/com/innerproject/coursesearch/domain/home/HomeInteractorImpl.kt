package com.innerproject.coursesearch.domain.home

import com.innerproject.coursesearch.data.home.HomeRepository
import com.innerproject.coursesearch.domain.models.Course
import com.innerproject.coursesearch.domain.models.Summary
import com.innerproject.coursesearch.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class HomeInteractorImpl(private val repository: HomeRepository): HomeInteractor {
    override suspend fun getCourses(number: Int): Flow<Pair<List<Course>?, String?>> {
        return repository.getCourses(number).map { result ->
            when(result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }
                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }

    override suspend fun getAllStars(): Flow<Pair<List<Summary>?, String?>> {
        return repository.getAllStars().map { result ->
            when(result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }
                is Resource.Error -> {
                    Pair(null, result.message)
                }
            }
        }
    }
}