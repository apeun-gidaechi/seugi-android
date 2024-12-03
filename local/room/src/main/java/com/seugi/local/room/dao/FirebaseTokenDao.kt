package com.seugi.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.seugi.local.room.base.BaseDao
import com.seugi.local.room.entity.FirebaseTokenEntity
import com.seugi.local.room.util.SeugiTable

@Dao
interface FirebaseTokenDao : BaseDao<FirebaseTokenEntity> {

    @Query("SELECT * FROM ${SeugiTable.FIREBASE_TOKEN_TABLE} WHERE idx = 0")
    suspend fun getToken(): FirebaseTokenEntity?

    @Query("DELETE FROM ${SeugiTable.FIREBASE_TOKEN_TABLE}")
    suspend fun deleteToken()
}
