package com.innerproject.coursesearch.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innerproject.coursesearch.domain.favourites.FavouritesInteractor
import com.innerproject.coursesearch.domain.models.Course
import com.innerproject.coursesearch.utils.CourseState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FavouritesViewModel(private val interactor: FavouritesInteractor) : ViewModel() {

    private val favouriteState = MutableLiveData<Boolean>()
    private val courseState = MutableLiveData<CourseState>()

    fun getFavourites(): LiveData<Boolean> = favouriteState
    fun getObserve(): LiveData<CourseState> = courseState

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

    fun getCourses() {
        viewModelScope.launch {
            interactor.getCourses().collect { courseList ->
                processResult(courseList)
            }
        }
    }

    private fun processResult(courseList: List<Course>) {
        if (courseList.isEmpty()) {
            renderState(CourseState.Empty("empty"))
        } else {
            renderState(CourseState.Content(courseList))
        }
    }

    private fun renderState(state: CourseState) {
        courseState.postValue(state)
    }
}