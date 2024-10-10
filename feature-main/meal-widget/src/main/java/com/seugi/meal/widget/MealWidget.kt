package com.seugi.meal.widget

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.InternalComposeApi
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.currentComposer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpSize
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
import androidx.glance.appwidget.provideContent
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.glance.background
import androidx.glance.unit.ColorProvider
import androidx.glance.currentState
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.seugi.meal.widget.definition.MealWidgetStateDefinition
import com.seugi.meal.widget.di.getGlanceWidgetManager
import com.seugi.meal.widget.di.getMealRepository
import com.seugi.meal.widget.di.getWorkspaceRepository
import com.seugi.meal.widget.model.MealWidgetState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.datetime.toKotlinLocalDate
import java.time.LocalDate


class MealWidget: GlanceAppWidget() {

    override val stateDefinition = MealWidgetStateDefinition

    override val sizeMode: SizeMode = SizeMode.Exact

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme {
                Content()
            }
        }
    }

    @Composable
    fun Content() {
        val context = LocalContext.current()
        val glanceId = LocalGlanceId.current()
        val size = LocalSize.current()

        val mealState = (LocalState.current() as MealWidgetState)


        Row(
            modifier = GlanceModifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            when (mealState) {
                MealWidgetState.Loading -> {
                    Text(
                        text = "하이!!",
                        style = TextStyle(color = ColorProvider(Color.Blue))
                    )
                }
                is MealWidgetState.Success -> {
                    Text(
                        text = "하이!! ${mealState.data.toString()}",
                        style = TextStyle(color = ColorProvider(Color.Red))
                    )
                }

                MealWidgetState.Error -> {}
            }
            Button(
                "", {}
            )

            LaunchedEffect(Unit){
                MealWorker.enqueue(
                    context = context,
                    size = size,
                    glanceId = glanceId
                )
            }
        }
    }

    override suspend fun onDelete(context: Context, glanceId: GlanceId) {
        super.onDelete(context, glanceId)
        MealWorker.cancel(context, glanceId)
    }

}

class MealWorker(private val context: Context, workParams: WorkerParameters): CoroutineWorker(context, workParams) {
    @SuppressLint("RestrictedApi")
    override suspend fun doWork(): Result {
        val workspaceRepository = getWorkspaceRepository(context)
        val mealRepository = getMealRepository(context)
        setWidgetState(MealWidgetState.Loading)
        mealRepository.getDateMeal(
            workspaceId = workspaceRepository.getWorkspaceId(),
            date = LocalDate.now().toKotlinLocalDate()
        ).collectLatest { item ->
            when (item) {
                is com.seugi.common.model.Result.Success -> {
                    setWidgetState(MealWidgetState.Success(item.data))
                }

                is com.seugi.common.model.Result.Error -> {
                    setWidgetState(MealWidgetState.Error)
                }
                com.seugi.common.model.Result.Loading -> {
                    setWidgetState(MealWidgetState.Loading)
                }
            }
        }
        return Result.Success()
    }

    private suspend fun setWidgetState(newState: MealWidgetState) {
        val manager = getGlanceWidgetManager(context)
        val glanceIds = manager.getGlanceIds(MealWidget::class.java)
        glanceIds.forEach { glanceId ->
            updateAppWidgetState(
                context = context,
                definition = MealWidgetStateDefinition,
                glanceId = glanceId,
                updateState = { newState }
            )
        }
        MealWidget().updateAll(context)
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


        fun cancel(context: Context, glanceId: GlanceId) {
            WorkManager.getInstance(context).cancelAllWorkByTag(glanceId.toString())
        }
    }

}

@OptIn(InternalComposeApi::class)
@Composable
internal fun <T> ProvidableCompositionLocal<T>.current() =
    currentComposer.consume(this)

class MealWidgetReceiver: GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = MealWidget()
}

const val MEAL_KEY = "MEAL"