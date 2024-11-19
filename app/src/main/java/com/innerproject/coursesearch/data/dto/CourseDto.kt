package com.innerproject.coursesearch.data.dto

import com.google.gson.annotations.SerializedName

data class CourseDto(
    val id: Int,
    val title: String,
    val summary: String?,
    @SerializedName("cover")
    val image: String?,
    @SerializedName("certificate_cover_org")
    val ownerImage: String?,
    val description: String?,
    val owner: String?,
    @SerializedName("became_published_at")
    val publishedDate: String?,
    @SerializedName("display_price")
    val price: String?,
    @SerializedName("review_summary")
    val rating: String,
    @SerializedName("canonical_url")
    val courseUrl: String?,
    val difficulty: String?
): Response()