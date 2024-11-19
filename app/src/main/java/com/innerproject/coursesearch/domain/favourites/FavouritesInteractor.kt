package com.innerproject.coursesearch.domain.favourites

import com.innerproject.coursesearch.domain.models.Course
import kotlinx.coroutines.flow.Flow

interface FavouritesInteractor {

    suspend fun addFavourites(course: Course)

    suspend fun deleteCourse(course: Course)

    suspend fun getCourses(): Flow<List<Course>>
}