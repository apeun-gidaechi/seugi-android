package com.seugi.meal.widget

import android.annotation.SuppressLint
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.Button
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.LocalGlanceId
import androidx.glance.LocalSize
import androidx.glance.LocalState
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.SizeMode
import androidx.glance.appwidget.cornerRadius
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.glance.background
import androidx.glance.unit.ColorProvider
import androidx.glance.currentState
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.seugi.meal.widget.definition.MealWidgetStateDefinition
import com.seugi.meal.widget.di.getGlanceWidgetManager
import com.seugi.meal.widget.di.getMealRepository
import com.seugi.meal.widget.di.getWorkspaceRepository
import com.seugi.meal.widget.model.MealWidgetState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.datetime.toKotlinLocalDate
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit


class MealWorker(private val context: Context, workParams: WorkerParameters): CoroutineWorker(context, workParams) {
    @SuppressLint("RestrictedApi")
    override suspend fun doWork(): Result {
        Log.d("TAG", "doWork: Launch Meal Load")
        val workspaceRepository = getWorkspaceRepository(context)
        val mealRepository = getMealRepository(context)
        setWidgetState(MealWidgetState.Loading)
        try {
            mealRepository.getDateMeal(
                workspaceId = workspaceRepository.getWorkspaceId(),
                date = LocalDate.now().toKotlinLocalDate()
            ).collectLatest { item ->
                when (item) {
                    is com.seugi.common.model.Result.Success -> {
                        setWidgetState(MealWidgetState.Success(LocalDateTime.now().toKotlinLocalDateTime(), item.data))
                    }

                    is com.seugi.common.model.Result.Error -> {
                        setWidgetState(MealWidgetState.Error)
                        throw item.throwable
                    }

                    com.seugi.common.model.Result.Loading -> {
                        setWidgetState(MealWidgetState.Loading)
                    }
                }
            }
        } catch (e: Exception) {
            return Result.Failure()
        }
        return Result.Success()
    }

    private suspend fun setWidgetState(newState: MealWidgetState) {
        val manager = getGlanceWidgetManager(context)
//        val glanceIds = manager.getGlanceIds(MealWidget::class.java)
//        glanceIds.forEach { glanceId ->
//            updateAppWidgetState(
//                context = context,
//                definition = MealWidgetStateDefinition,
//                glanceId = glanceId,
//                updateState = { newState }
//            )
//        }
//        MealWidget().updateAll(context)
    }

    companion object {

        private const val WIDTH_KEY = "width"
        private const val HEIGHT_KEY = "height"
        private const val FORCE_KEY = "force"

        private val uniqueWorkName = MealWorker::class.java.simpleName

        fun enqueue(context: Context, size: DpSize, glanceId: GlanceId, force: Boolean = false) {
            val manager = WorkManager.getInstance(context)
            val requestBuilder = OneTimeWorkRequestBuilder<MealWorker>().apply {
                addTag(glanceId.toString())
                setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                setInputData(
                    Data.Builder()
                        .putFloat(WIDTH_KEY, size.width.value)
                        .putFloat(HEIGHT_KEY, size.height.value)
                        .putBoolean(FORCE_KEY, force)
                        .build()
                )
            }
            val workPolicy = if (force) {
                ExistingWorkPolicy.REPLACE
            } else {
                ExistingWorkPolicy.KEEP
            }

            manager.enqueueUniqueWork(
                uniqueWorkName + size.width + size.height,
                workPolicy,
                requestBuilder.build()
            )
        }

        fun enqueuePeriodic(context: Context) {
            val constraints = Constraints.Builder()
                .build()

            val workManager = WorkManager.getInstance(context)
            val periodicWorkRequest = PeriodicWorkRequestBuilder<MealWorker>(900, TimeUnit.SECONDS)
                .setConstraints(constraints)
                .build()

            workManager.enqueueUniquePeriodicWork(
                "MealUpdateWork",
                ExistingPeriodicWorkPolicy.CANCEL_AND_REENQUEUE,
                periodicWorkRequest
            )
        }


        fun cancel(context: Context, glanceId: GlanceId) {
            WorkManager.getInstance(context).cancelAllWorkByTag(glanceId.toString())
        }
    }

}

@OptIn(InternalComposeApi::class)
@Composable
internal fun <T> ProvidableCompositionLocal<T>.current() =
    currentComposer.consume(this)

class MealWidgetReceiver: AppWidgetProvider() {

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)

    }

    override fun onEnabled(context: Context?) {
        super.onEnabled(context)
//        pro
    }
}
