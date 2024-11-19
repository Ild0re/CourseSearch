package com.innerproject.coursesearch.data.db

import com.innerproject.coursesearch.domain.models.Course

class CourseDbConvertor {

    fun mapToEntity(course: Course): CourseEntity {
        return CourseEntity(
            course.id,
            course.title,
            course.summary,
            course.image,
            course.ownerImage,
            course.description,
            course.owner,
            course.publishedDate,
            course.price,
            course.rating,
            course.courseUrl,
            course.difficulty,
            course.inFavourites
        )
    }

    fun mapToModel(courseEntity: CourseEntity): Course {
        return Course(
            courseEntity.id,
            courseEntity.title,
            courseEntity.summary,
            courseEntity.image,
            courseEntity.ownerImage,
            courseEntity.description,
            courseEntity.owner,
            courseEntity.publishedDate,
            courseEntity.price,
            courseEntity.rating,
            courseEntity.courseUrl,
            courseEntity.difficulty,
            courseEntity.inFavourites
        )
    }
}