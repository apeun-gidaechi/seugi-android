package com.seugi.meal.widget.di

import android.content.Context
import androidx.glance.appwidget.GlanceAppWidgetManager
import com.seugi.data.meal.MealRepository
import com.seugi.data.workspace.WorkspaceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WidgetModule {

    @Provides
    @Singleton
    fun providesGlanceWidgetManager(@ApplicationContext context: Context): GlanceAppWidgetManager = GlanceAppWidgetManager(context)
}

internal fun getMealRepository(context: Context): MealRepository {
    val hiltEntryPoint = EntryPointAccessors.fromApplication(context, MealWidgetEntryPoint::class.java)
    return hiltEntryPoint.mealRepository()
}

internal fun getWorkspaceRepository(context: Context): WorkspaceRepository {
    val hiltEntryPoint = EntryPointAccessors.fromApplication(context, MealWidgetEntryPoint::class.java)
    return hiltEntryPoint.workspaceRepository()
}

internal fun getGlanceWidgetManager(context: Context): GlanceAppWidgetManager {
    val hiltEntryPoint = EntryPointAccessors.fromApplication(context, MealWidgetEntryPoint::class.java)
    return hiltEntryPoint.glanceAppWidgetManager()
}
