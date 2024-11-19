package com.innerproject.coursesearch.ui.course

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innerproject.coursesearch.domain.favourites.FavouritesInteractor
import com.innerproject.coursesearch.domain.models.Course
import kotlinx.coroutines.launch

class CourseViewModel(private val interactor: FavouritesInteractor) : ViewModel() {

    private val favouriteState = MutableLiveData<Boolean>()

    fun getFavourites(): LiveData<Boolean> = favouriteState

    fun addCourseToFavourites(course: Course) {
        viewModelScope.launch {
            course.inFavourites = true
            interactor.addFavourites(course)
            favouriteState.postValue(true)
        }
    }

    fun deleteCourseFromFavourites(course: Course) {
        viewModelScope.launch {
            course.inFavourites = false
            interactor.deleteCourse(course)
            favouriteState.postValue(false)
        }
    }
}