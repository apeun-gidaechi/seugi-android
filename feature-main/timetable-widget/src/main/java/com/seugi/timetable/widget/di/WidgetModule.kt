package com.seugi.timetable.widget.di

import android.content.Context
import com.seugi.data.timetable.TimetableRepository
import com.seugi.data.workspace.WorkspaceRepository
import dagger.hilt.android.EntryPointAccessors

internal fun getTimetableRepository(context: Context): TimetableRepository {
    val hiltEntryPoint = EntryPointAccessors.fromApplication(context, TimetableWidgetEntryPoint::class.java)
    return hiltEntryPoint.timetableRepository()
}

internal fun getWorkspaceRepository(context: Context): WorkspaceRepository {
    val hiltEntryPoint = EntryPointAccessors.fromApplication(context, TimetableWidgetEntryPoint::class.java)
    return hiltEntryPoint.workspaceRepository()
}
