package com.innerproject.coursesearch.domain.models

data class Course(
    val id: Int,
    val title: String,
    val summary: String?,
    val image: String?,
    val ownerImage: String?,
    val description: String?,
    val owner: String?,
    val publishedDate: String?,
    val price: String?,
    val rating: String,
    val courseUrl: String?,
    val difficulty: String?,
    var inFavourites: Boolean = false
)