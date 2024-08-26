package com.seugi.data.meal.repository

import android.util.Log
import com.seugi.common.model.Result
import com.seugi.common.model.asResult
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.common.utiles.isEmptyGetNull
import com.seugi.common.utiles.toNotSpaceString
import com.seugi.data.meal.MealRepository
import com.seugi.data.meal.mapper.toEntity
import com.seugi.data.meal.mapper.toModels
import com.seugi.data.meal.response.MealModel
import com.seugi.local.room.dao.MealDao
import com.seugi.network.core.response.safeResponse
import com.seugi.network.meal.MealDataSource
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.LocalDate

class MealRepositoryImpl @Inject constructor(
    private val dataSource: MealDataSource,
    private val mealDao: MealDao,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher,
) : MealRepository {
    override suspend fun getDateMeal(workspaceId: String, date: LocalDate): Flow<Result<ImmutableList<MealModel>>> = flow {
        val response: ImmutableList<MealModel> =
            mealDao.getDateMeals(
                workspaceId = workspaceId,
                date = date.toNotSpaceString(),
            )
                .apply {
                    Log.d("TAG", "getDateMeal: $this")
                }
                ?.isEmptyGetNull()
                ?.toModels()
                .apply {
                    Log.d("TAG", "getDateMeal: $this")
                }
                ?.toImmutableList()
                .apply {
                    Log.d("TAG", "getDateMeal: $this")
                }
                ?: dataSource.getDateMeal(
                    workspaceId = workspaceId,
                    date = date.toNotSpaceString(),
                ).safeResponse()
                    .toModels()
                    .toImmutableList().also {
                        mealDao.insert(it.toEntity())
                    }
        Log.d("TAG", "getDateMeal: $response")
        emit(response)
    }
        .flowOn(dispatcher)
        .asResult()
}
