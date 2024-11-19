package com.innerproject.coursesearch.di

import com.innerproject.coursesearch.data.db.CourseDbConvertor
import com.innerproject.coursesearch.data.favourites.FavouritesRepository
import com.innerproject.coursesearch.data.favourites.FavouritesRepositoryImpl
import com.innerproject.coursesearch.data.home.HomeRepository
import com.innerproject.coursesearch.data.home.HomeRepositoryImpl
import com.innerproject.coursesearch.domain.models.Course
import org.koin.dsl.module

val repositoryModule = module {
    single<HomeRepository> {
        HomeRepositoryImpl(get(), get(), get())
    }

    factory { CourseDbConvertor() }

    single<FavouritesRepository> {
        FavouritesRepositoryImpl(get(), get())
    }

}