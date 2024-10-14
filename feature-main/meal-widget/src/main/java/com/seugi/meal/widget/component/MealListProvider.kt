package com.seugi.meal.widget.component

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.seugi.meal.widget.MealWidgetReceiver.Companion.CONTENT
import com.seugi.meal.widget.R

class MealListProvider(private val context: Context, intent: Intent) :
    RemoteViewsService.RemoteViewsFactory {
    private val content: List<String> = intent.getStringArrayListExtra(CONTENT) ?: emptyList()

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
        remoteView.setTextViewText(R.id.text_menu, content[position])
        return remoteView
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }
}
