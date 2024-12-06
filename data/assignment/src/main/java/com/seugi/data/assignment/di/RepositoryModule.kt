package com.seugi.data.assignment.di

import com.seugi.data.assignment.AssignmentRepository
import com.seugi.data.assignment.repository.AssignmentRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun bindsAssignmentRepository(taskRepositoryImpl: AssignmentRepositoryImpl): AssignmentRepository
}
