package com.innerproject.coursesearch.data.favourites

import com.innerproject.coursesearch.domain.models.Course
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {

    suspend fun addFavourites(course: Course)

    suspend fun deleteFavourites(course: Course)

    suspend fun getFavourites(): Flow<List<Course>>
}