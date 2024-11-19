package com.innerproject.coursesearch.data.dto

data class CourseResponse(
    val meta: MetaDto,
    val courses: List<CourseDto>,
    val enrollments: List<Any>
): Response()