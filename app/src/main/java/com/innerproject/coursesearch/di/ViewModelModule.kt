package com.innerproject.coursesearch.di

import com.innerproject.coursesearch.ui.course.CourseViewModel
import com.innerproject.coursesearch.ui.favourites.FavouritesViewModel
import com.innerproject.coursesearch.ui.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HomeViewModel(get(), get())
    }

    viewModel {
        FavouritesViewModel(get())
    }

    viewModel {
        CourseViewModel(get())
    }
}