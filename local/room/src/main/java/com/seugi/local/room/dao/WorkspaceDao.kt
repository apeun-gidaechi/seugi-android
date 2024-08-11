package com.seugi.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.seugi.local.room.base.BaseDao
import com.seugi.local.room.model.TokenEntity
import com.seugi.local.room.model.WorkspaceEntity
import com.seugi.local.room.util.SeugiTable

@Dao
interface WorkspaceDao: BaseDao<WorkspaceEntity> {
    @Query("SELECT * FROM ${SeugiTable.WORKSPACE_TABLE}")
    suspend fun getWorkspace(): List<WorkspaceEntity?>

    @Query("DELETE FROM ${SeugiTable.WORKSPACE_TABLE}")
    suspend fun deleteWorkspace()
}