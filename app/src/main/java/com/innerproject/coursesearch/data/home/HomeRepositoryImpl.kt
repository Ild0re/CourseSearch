package com.innerproject.coursesearch.data.home

import com.innerproject.coursesearch.data.db.AppDatabase
import com.innerproject.coursesearch.data.db.CourseDbConvertor
import com.innerproject.coursesearch.data.db.CourseEntity
import com.innerproject.coursesearch.data.dto.CourseResponse
import com.innerproject.coursesearch.data.dto.SummaryResponse
import com.innerproject.coursesearch.data.dto.SummaryDto
import com.innerproject.coursesearch.data.network.NetworkClient
import com.innerproject.coursesearch.domain.models.Course
import com.innerproject.coursesearch.domain.models.Summary
import com.innerproject.coursesearch.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeParseException


class HomeRepositoryImpl(
    private val networkClient: NetworkClient,
    private val appDatabase: AppDatabase, private val convertor: CourseDbConvertor
) : HomeRepository {
    override suspend fun getCourses(number: Int): Flow<Resource<List<Course>>> = flow {
        val response = networkClient.getCourses(number)
        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error("Ошибка подключения к интернету"))
            }

            200 -> with(response as CourseResponse) {
                val data = courses.map {
                    Course(
                        it.id,
                        it.title,
                        it.summary,
                        it.image,
                        it.ownerImage,
                        it.description,
                        it.owner,
                        if (it.publishedDate != null) {
                            formatDate(it.publishedDate)
                        } else {
                            null
                        },
                        it.price,
                        it.rating,
                        it.courseUrl,
                        it.difficulty
                    )
                }
                val courseListEntity = appDatabase.courseDao().getCourses()
                val courseList = convertFromCourseEntity(courseListEntity)
                for (i in data) {
                    if (i in courseList) {
                        i.inFavourites = true
                    } else {
                        i.inFavourites = false
                    }
                }
                emit(Resource.Success(data))
            }

            else -> {
                emit(Resource.Error("Ошибка сервера"))
            }
        }
    }

    override suspend fun getAllStars(): Flow<Resource<List<Summary>>> = flow {
        val response = networkClient.getAllStars()
        when (response.resultCode) {
            -1 -> {
                emit(Resource.Error("Ошибка подключения к интернету"))
            }

            200 -> with(response as SummaryResponse) {
                val data = summaries.map {
                    Summary(
                        it.id,
                        it.course,
                        it.average
                    )
                }
                emit(Resource.Success(data))
            }

            else -> {
                emit(Resource.Error("Ошибка сервера"))
            }
        }
    }


    private fun formatDate(dateString: String): String {
        try {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val dateTime = LocalDateTime.parse(dateString, formatter)
            val zonedDateTime = dateTime.atZone(ZoneId.of("UTC"))
            val localDateTime = zonedDateTime.toLocalDateTime()

            val day = localDateTime.dayOfMonth.toString().padStart(2, '0')
            val month = getMonthName(localDateTime.monthValue)
            val year = localDateTime.year

            return "$day $month $year"
        } catch (e: DateTimeParseException) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            val dateTime = LocalDateTime.parse(dateString, formatter)
            val zonedDateTime = dateTime.atZone(ZoneId.of("UTC"))
            val localDateTime = zonedDateTime.toLocalDateTime()

            val day = localDateTime.dayOfMonth.toString().padStart(2, '0')
            val month = getMonthName(localDateTime.monthValue)
            val year = localDateTime.year

            return "$day $month $year"
        }

    }

    private fun getMonthName(monthNumber: Int): String {
        return when (monthNumber) {
            1 -> "Января"
            2 -> "Февраля"
            3 -> "Марта"
            4 -> "Апреля"
            5 -> "Мая"
            6 -> "Июня"
            7 -> "Июля"
            8 -> "Августа"
            9 -> "Сентября"
            10 -> "Октября"
            11 -> "Ноября"
            12 -> "Декабря"
            else -> throw IllegalArgumentException("Invalid month number: $monthNumber")
        }
    }

    private fun convertFromCourseEntity(courseList: List<CourseEntity>): List<Course> {
        return courseList.map { course -> convertor.mapToModel(course) }
    }
}