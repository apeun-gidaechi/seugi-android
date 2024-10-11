package com.seugi.meal.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.seugi.common.model.Result
import com.seugi.data.meal.response.MealModel
import com.seugi.data.meal.response.MealType
import com.seugi.meal.widget.di.getMealRepository
import com.seugi.meal.widget.di.getWorkspaceRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.toKotlinLocalDate
import java.time.LocalDate
import java.time.LocalTime

private fun fetchMealData(
    context: Context,
    block: (Result<ImmutableList<MealModel>>) -> Unit
)  {
    CoroutineScope(Dispatchers.IO).launch {
        val workspaceRepository = getWorkspaceRepository(context)
        val mealRepository = getMealRepository(context)
        mealRepository.getDateMeal(
            workspaceId = workspaceRepository.getWorkspaceId(),
            date = LocalDate.now().toKotlinLocalDate()
        ).collectLatest(block)
    }
}

fun updateAppWidgetUI(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int, data: ImmutableList<MealModel>) {
    val views = RemoteViews(context.packageName, R.layout.widget_meal_layout)

    val time = LocalTime.now()


    val mealType: MealType =
        if (time.isBefore(LocalTime.of(8, 10))) MealType.BREAKFAST
        else if (time.isBefore(LocalTime.of(13, 30))) MealType.LUNCH
        else MealType.BREAKFAST

    val item = data.find { it.mealType == mealType }

    val category =
        when (mealType) {
            MealType.BREAKFAST -> "아침"
            MealType.LUNCH -> "점심"
            MealType.DINNER -> "저녁"
        }

    if (item != null) {
        views.setTextViewText(R.id.text_category, category)
        views.setTextViewText(R.id.text_kcal, item.calorie)
        views.setTextViewText(R.id.text_menu, item.menu.reduce { acc, s -> "${acc}\n${s}" })
    }

    // 위젯 업데이트
    appWidgetManager.updateAppWidget(appWidgetId, views)
}

class MealWidgetReceiver: AppWidgetProvider() {

    override fun onUpdate(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetIds: IntArray?
    ) {
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
                                data = it.data
                            )
                        }
                        is Result.Error -> {
                            it.throwable.printStackTrace()
                        }
                        Result.Loading -> {}
                    }
                }
            )
        }
    }
}
