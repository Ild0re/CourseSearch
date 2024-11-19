package com.innerproject.coursesearch.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innerproject.coursesearch.domain.favourites.FavouritesInteractor
import com.innerproject.coursesearch.domain.home.HomeInteractor
import com.innerproject.coursesearch.domain.models.Course
import com.innerproject.coursesearch.utils.ScreenState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(
    private val interactor: HomeInteractor,
    private val favourites: FavouritesInteractor
) : ViewModel() {

    private val state = MutableLiveData<ScreenState>()
    private val favouriteState = MutableLiveData<Boolean>()

    fun getState(): LiveData<ScreenState> = state
    fun getFavourites(): LiveData<Boolean> = favouriteState

    private var searchJob: Job? = null

    fun clearJob() {
        searchJob?.cancel()
    }

    fun loadData(number: Int) {
        clearJob()
        state.value = ScreenState.Loading

        viewModelScope.launch {
            interactor
                .getCourses(number)
                .collect { pair ->
                    processResult(pair.first, pair.second)
                }
        }
    }

    fun loadNewData(number: Int) {
        viewModelScope.launch {
            interactor
                .getCourses(number)
                .collect { pair ->
                    processResult(pair.first, pair.second)
                }
        }
    }

    fun addCourseToFavourites(course: Course) {
        viewModelScope.launch {
            course.inFavourites = true
            favourites.addFavourites(course)
            favouriteState.postValue(true)
        }
    }

    fun deleteCourseFromFavourites(course: Course) {
        viewModelScope.launch {
            course.inFavourites = false
            favourites.deleteCourse(course)
            favouriteState.postValue(false)
        }
    }



    private fun processResult(data: List<Course>?, errorMessage: String?) {
        val courses = mutableListOf<Course>()
        if (data != null) {
            courses.addAll(data)
        }
        when {
            errorMessage != null -> {
                val error = ScreenState.Error("Ошибка со связью")
                Log.e("ERROR", errorMessage)
                state.postValue(error)
            }

            courses.isEmpty() -> {
                val empty = ScreenState.Empty("Ничего не нашлось")
                state.postValue(empty)
            }

            else -> {
                val content = ScreenState.Content(courses)
                state.postValue(content)
            }
        }
    }
}