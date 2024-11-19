package com.innerproject.coursesearch.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CourseDao {

    @Insert(entity = CourseEntity::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCourseToFavourites(courseEntity: CourseEntity)

    @Delete(entity = CourseEntity::class)
    suspend fun deleteCourse(courseEntity: CourseEntity)

    @Query("SELECT * FROM course_table")
    suspend fun getCourses(): List<CourseEntity>
}