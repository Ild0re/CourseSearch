package com.innerproject.coursesearch.data.favourites

import com.innerproject.coursesearch.data.db.AppDatabase
import com.innerproject.coursesearch.data.db.CourseDbConvertor
import com.innerproject.coursesearch.data.db.CourseEntity
import com.innerproject.coursesearch.domain.models.Course
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavouritesRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val convertor: CourseDbConvertor
) : FavouritesRepository {
    override suspend fun addFavourites(course: Course) {
        val courseEntity = convertor.mapToEntity(course)
        appDatabase.courseDao().addCourseToFavourites(courseEntity)
    }

    override suspend fun deleteFavourites(course: Course) {
        val courseEntity = convertor.mapToEntity(course)
        appDatabase.courseDao().deleteCourse(courseEntity)
    }

    override suspend fun getFavourites(): Flow<List<Course>> = flow {
        val courseListEntity = appDatabase.courseDao().getCourses()
        val courseList = convertFromCourseEntity(courseListEntity)
        emit(courseList)
    }

    private fun convertFromCourseEntity(courseList: List<CourseEntity>): List<Course> {
        return courseList.map { course -> convertor.mapToModel(course) }
    }
}