package com.seugi.meal.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.seugi.common.model.Result
import com.seugi.data.core.model.MealModel
import com.seugi.data.core.model.MealType
import com.seugi.meal.widget.MealWidgetReceiver.Companion.CONTENT
import com.seugi.meal.widget.component.MealListProvider
import com.seugi.meal.widget.di.getMealRepository
import com.seugi.meal.widget.di.getWorkspaceRepository
import java.time.LocalDate
import java.time.LocalTime
import java.util.ArrayList
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinLocalDate

private fun fetchMealData(context: Context, block: (Result<ImmutableList<com.seugi.data.core.model.MealModel>>) -> Unit) {
    CoroutineScope(Dispatchers.IO).launch {
        val workspaceRepository = getWorkspaceRepository(context)
        val mealRepository = getMealRepository(context)
        mealRepository.getDateMeal(
            workspaceId = workspaceRepository.getLocalWorkspaceId(),
            date = LocalDate.now().toKotlinLocalDate(),
        ).collectLatest(block)
    }
}

fun updateAppWidgetUI(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int, data: ImmutableList<com.seugi.data.core.model.MealModel>) {
    val views = RemoteViews(context.packageName, R.layout.widget_meal_layout)

    val time = LocalTime.now()

    val mealType: com.seugi.data.core.model.MealType =
        if (time.isBefore(LocalTime.of(8, 10))) {
            com.seugi.data.core.model.MealType.BREAKFAST
        } else if (time.isBefore(LocalTime.of(13, 30))) {
            com.seugi.data.core.model.MealType.LUNCH
        } else {
            com.seugi.data.core.model.MealType.DINNER
        }

    val item = data.find { it.mealType == mealType }

    val category =
        when (mealType) {
            com.seugi.data.core.model.MealType.BREAKFAST -> "아침"
            com.seugi.data.core.model.MealType.LUNCH -> "점심"
            com.seugi.data.core.model.MealType.DINNER -> "저녁"
        }

    val intent = Intent(context, MealWidgetService::class.java)

    if (item != null) {
        views.setTextViewText(R.id.text_kcal, item.calorie)
        intent.putExtra(CONTENT, ArrayList<String>().apply { addAll(item.menu) })
    } else {
        views.setTextViewText(R.id.text_kcal, "")
        intent.putExtra(CONTENT, ArrayList<String>().apply { add("급식이 존재하지 않습니다.") })
    }
    views.setTextViewText(R.id.text_category, category)
    views.setRemoteAdapter(R.id.list_menu, intent)

    // 위젯 업데이트
    appWidgetManager.updateAppWidget(appWidgetId, views)
}

class MealWidgetService : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsFactory {
        return MealListProvider(this.applicationContext, intent)
    }
}

class MealWidgetReceiver : AppWidgetProvider() {

    override fun onUpdate(context: Context?, appWidgetManager: AppWidgetManager?, appWidgetIds: IntArray?) {
        if (context == null) return
        if (appWidgetManager == null) return
        if (appWidgetIds == null) return

        appWidgetIds.forEach { appWidgetId ->
            fetchMealData(
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
