package com.seugi.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seugi.local.room.base.BaseDao
import com.seugi.local.room.model.WorkspaceEntity
import com.seugi.local.room.util.SeugiTable

@Dao
interface WorkspaceDao : BaseDao<WorkspaceEntity> {
    @Query("SELECT * FROM ${SeugiTable.WORKSPACE_TABLE}")
    suspend fun getWorkspaceId(): WorkspaceEntity

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWorkspaces(workspaces: List<WorkspaceEntity>)

    @Query("DELETE FROM ${SeugiTable.WORKSPACE_TABLE}")
    suspend fun deleteWorkspace()
}
