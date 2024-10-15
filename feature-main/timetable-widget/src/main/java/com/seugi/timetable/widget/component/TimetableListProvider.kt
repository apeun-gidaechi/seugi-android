package com.seugi.timetable.widget.component

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.seugi.timetable.widget.R
import com.seugi.timetable.widget.TimetableWidgetReceiver

class TimetableListProvider(private val context: Context, intent: Intent) :
    RemoteViewsService.RemoteViewsFactory {
    private val content: List<String> = intent.getStringArrayListExtra(TimetableWidgetReceiver.CONTENT) ?: emptyList()

    override fun onCreate() {}

    override fun onDataSetChanged() {}

    override fun onDestroy() {}

    override fun getCount(): Int {
        return content.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    /*
     *Similar to getView of Adapter where instead of View
     *we return RemoteViews
     *
     */
    override fun getViewAt(position: Int): RemoteViews {
        val remoteView = RemoteViews(context.packageName, R.layout.item_meal_menu)
        remoteView.setTextViewText(R.id.text_menu, "${position + 1}교시 : ${content[position]}")
        return remoteView
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }
}
