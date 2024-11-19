package com.innerproject.coursesearch.utils

import com.innerproject.coursesearch.domain.models.Course

sealed interface ScreenState {
    object Loading : ScreenState
    data class Empty(val message: String) : ScreenState
    data class Error(val message: String) : ScreenState
    data class Content(val data: List<Course>) : ScreenState
}