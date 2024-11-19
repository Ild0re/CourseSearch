package com.innerproject.coursesearch.di

import com.innerproject.coursesearch.domain.favourites.FavouritesInteractor
import com.innerproject.coursesearch.domain.favourites.FavouritesInteractorImpl
import com.innerproject.coursesearch.domain.home.HomeInteractor
import com.innerproject.coursesearch.domain.home.HomeInteractorImpl
import org.koin.dsl.module

val interactorModule = module {
    single<HomeInteractor> {
        HomeInteractorImpl(get())
    }

    single<FavouritesInteractor> {
        FavouritesInteractorImpl(get())
    }
}