package com.innerproject.coursesearch.domain.favourites

import com.innerproject.coursesearch.data.favourites.FavouritesRepository
import com.innerproject.coursesearch.domain.models.Course
import kotlinx.coroutines.flow.Flow

class FavouritesInteractorImpl(private val reposiroty: FavouritesRepository) :
    FavouritesInteractor {
    override suspend fun addFavourites(course: Course) {
        return reposiroty.addFavourites(course)
    }

    override suspend fun deleteCourse(course: Course) {
        return reposiroty.deleteFavourites(course)
    }

    override suspend fun getCourses(): Flow<List<Course>> {
        return reposiroty.getFavourites()
    }
}