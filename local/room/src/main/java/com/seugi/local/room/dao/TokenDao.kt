package com.seugi.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.seugi.local.room.base.BaseDao
import com.seugi.local.room.model.TokenEntity
import com.seugi.local.room.util.SeugiTable

@Dao
interface TokenDao : BaseDao<TokenEntity> {
    @Query("SELECT * FROM ${SeugiTable.TOKEN_TABLE} WHERE idx = 0")
    suspend fun getToken(): TokenEntity?

    @Query("DELETE FROM ${SeugiTable.TOKEN_TABLE}")
    suspend fun deleteToken()
}
