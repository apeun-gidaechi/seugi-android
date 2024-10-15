package com.seugi.timetable.widget.di

import com.seugi.data.timetable.TimetableRepository
import com.seugi.data.workspace.WorkspaceRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface TimetableWidgetEntryPoint {
    fun timetableRepository(): TimetableRepository
    fun workspaceRepository(): WorkspaceRepository
}
