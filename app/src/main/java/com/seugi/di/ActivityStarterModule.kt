package com.seugi.di

import android.content.Context
import com.seugi.common.utiles.SeugiActivityStarter
import com.seugi.starter.SeugiActivityStarterImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ActivityStarterModule {

    @Provides
    @Singleton
    fun providesSeugiActivityStarter(@ApplicationContext context: Context): SeugiActivityStarter = SeugiActivityStarterImpl(context)
}
