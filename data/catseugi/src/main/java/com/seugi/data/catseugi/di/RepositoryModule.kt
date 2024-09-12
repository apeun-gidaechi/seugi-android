package com.seugi.data.catseugi.di

import com.seugi.data.catseugi.CatSeugiRepository
import com.seugi.data.catseugi.repository.CatSeugiRepositoryImpl
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
    fun bindsCatSeugiRepository(catSeugiRepositoryImpl: CatSeugiRepositoryImpl): CatSeugiRepository
}