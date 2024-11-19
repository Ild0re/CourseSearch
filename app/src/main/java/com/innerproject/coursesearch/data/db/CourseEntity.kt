package com.innerproject.coursesearch.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course_table")
data class CourseEntity(
    @PrimaryKey
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
    var inFavourites: Boolean
)