package com.seugi.data.meal.di

import com.seugi.data.meal.MealRepository
import com.seugi.data.meal.repository.MealRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindsMealRepository(mealRepositoryImpl: MealRepositoryImpl): MealRepository
}
