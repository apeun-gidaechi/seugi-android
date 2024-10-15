package com.seugi.timetable.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.seugi.common.model.Result
import com.seugi.data.timetable.model.TimetableModel
import com.seugi.timetable.widget.component.TimetableListProvider
import com.seugi.timetable.widget.di.getTimetableRepository
import com.seugi.timetable.widget.di.getWorkspaceRepository
import java.time.LocalDate
import kotlin.collections.ArrayList
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinLocalDate

private fun fetchTimetableData(context: Context, block: (Result<ImmutableList<TimetableModel>>) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        val workspaceRepository = getWorkspaceRepository(context)
        val timetableRepository = getTimetableRepository(context)
        timetableRepository.getTimetableDay(
            workspaceId = workspaceRepository.getLocalWorkspaceId(),
        ).collectLatest(block)
    }
}

fun updateAppWidgetUI(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int, data: ImmutableList<TimetableModel>) {
    val views = RemoteViews(context.packageName, R.layout.widget_timetable_layout)
    val date = LocalDate.now().toKotlinLocalDate()

    val filterTimetable = data
        .filter {
            it.date == date
        }
        .sortedBy {
            it.time
        }

    Log.d("TAG", "updateAppWidgetUI: $filterTimetable")

    val intent = Intent(context, TimetableWidgetService::class.java)

    if (filterTimetable.isNotEmpty()) {
        views.setTextViewText(R.id.text_date, "${date.monthNumber}.${date.dayOfMonth}")
        intent.putExtra(TimetableWidgetReceiver.CONTENT, ArrayList<String>().apply { addAll(filterTimetable.map { it.subject }) })
    } else {
        intent.putExtra(TimetableWidgetReceiver.CONTENT, ArrayList<String>().apply { add("시간표가 존재하지 않습니다.") })
    }

    views.setRemoteAdapter(R.id.list_timetable, intent)

    // 위젯 업데이트
    appWidgetManager.updateAppWidget(appWidgetId, views)
}

class TimetableWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return TimetableListProvider(this.applicationContext, intent)
    }
}

class TimetableWidgetReceiver : AppWidgetProvider() {

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        if (context == null) return
        if (appWidgetManager == null) return
        if (appWidgetIds == null) return

        appWidgetIds.forEach { appWidgetId ->
            fetchTimetableData(
                context = context,
                block = {
                    when (it) {
                        is Result.Success -> {
                            updateAppWidgetUI(
                                context = context,
                                appWidgetManager = appWidgetManager,
                                appWidgetId = appWidgetId,
                                data = it.data,
                            )
                        }
                        is Result.Error -> {
                            it.throwable.printStackTrace()
                        }
                        Result.Loading -> {}
                    }
                },
            )
        }
    }
    companion object {
        const val CONTENT = "content"
    }
}
