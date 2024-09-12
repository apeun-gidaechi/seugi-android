package com.seugi.data.catseugi

import com.seugi.common.model.Result
import kotlinx.coroutines.flow.Flow

interface CatSeugiRepository {

    suspend fun sendText(text: String): Flow<Result<String>>
}