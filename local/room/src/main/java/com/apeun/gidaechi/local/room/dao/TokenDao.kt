package com.apeun.gidaechi.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.apeun.gidaechi.local.room.base.BaseDao
import com.apeun.gidaechi.local.room.model.TokenEntity
import com.apeun.gidaechi.local.room.util.SeugiTable

@Dao
interface TokenDao: BaseDao<TokenEntity> {
    @Query("SELECT * FROM ${SeugiTable.TOKEN_TABLE} WHERE idx = 0")
    suspend fun getToken(): TokenEntity?

    @Query("DELETE FROM ${SeugiTable.TOKEN_TABLE}")
    suspend fun deleteToken()

}