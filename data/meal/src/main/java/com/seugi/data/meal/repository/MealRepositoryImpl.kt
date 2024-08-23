package com.seugi.data.meal.repository

import com.seugi.common.model.Result
import com.seugi.common.model.asResult
import com.seugi.common.utiles.DispatcherType
import com.seugi.common.utiles.SeugiDispatcher
import com.seugi.common.utiles.toNotSpaceString
import com.seugi.data.meal.MealRepository
import com.seugi.data.meal.mapper.toModels
import com.seugi.data.meal.response.MealModel
import com.seugi.network.core.response.safeResponse
import com.seugi.network.meal.MealDataSource
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.datetime.LocalDate
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val dataSource: MealDataSource,
    @SeugiDispatcher(DispatcherType.IO) private val dispatcher: CoroutineDispatcher
): MealRepository {
    override suspend fun getDateMeal(
        workspaceId: String,
        date: LocalDate,
    ): Flow<Result<ImmutableList<MealModel>>> = flow {
        val response = dataSource.getDateMeal(
            workspaceId = workspaceId,
            date = date.toNotSpaceString()
        ).safeResponse()
        emit(response.toModels().toImmutableList())
    }
        .flowOn(dispatcher)
        .asResult()

}