package com.seugi.meal.widget.di

import androidx.glance.appwidget.GlanceAppWidgetManager
import com.seugi.data.meal.MealRepository
import com.seugi.data.workspace.WorkspaceRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface MealWidgetEntryPoint {
    fun mealRepository(): MealRepository
    fun workspaceRepository(): WorkspaceRepository
    fun glanceAppWidgetManager(): GlanceAppWidgetManager
}