package com.innerproject.coursesearch.utils

import com.innerproject.coursesearch.domain.models.Course

sealed interface CourseState {

    data class Content(
        val courseList: List<Course>
    ) : CourseState

    data class Empty(
        val message: String
    ) : CourseState
}