package com.seugi.network.meal.di

import com.seugi.network.meal.MealDataSource
import com.seugi.network.meal.datasource.MealDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    @Singleton
    fun bindsMealDataSource(mealDataSourceImpl: MealDataSourceImpl): MealDataSource
}