package com.innerproject.coursesearch.data.network

import com.innerproject.coursesearch.data.dto.CourseResponse
import com.innerproject.coursesearch.data.dto.SummaryResponse
import com.innerproject.coursesearch.data.dto.TagsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CourseAPI {
    @GET("/api/courses?order=became_published_at")
    suspend fun getCourses(@Query("page") number: Int): CourseResponse

    @GET("/api/tags/")
    suspend fun getAllTags(): TagsResponse

    @GET("/api/tags/{pk}")
    suspend fun getTag(@Path("pk") pk: Int): TagsResponse

    @GET("/api/courses")
    suspend fun getCoursesByTag(@Query("tag") number: Int): CourseResponse

    @GET("/api/course-review-summaries")
    suspend fun getAllStars(): SummaryResponse

    @GET("/api/course-review-summaries/{pk}")
    suspend fun getStarFromCourse(@Path("pk") pk: Int): SummaryResponse


}