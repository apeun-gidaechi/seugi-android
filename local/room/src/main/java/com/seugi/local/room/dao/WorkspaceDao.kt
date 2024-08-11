package com.seugi.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.seugi.local.room.base.BaseDao
import com.seugi.local.room.model.TokenEntity
import com.seugi.local.room.model.WorkspaceEntity
import com.seugi.local.room.util.SeugiTable

@Dao
interface WorkspaceDao: BaseDao<WorkspaceEntity> {
    @Query("SELECT * FROM ${SeugiTable.WORKSPACE_TABLE}")
    suspend fun getWorkspace(): List<WorkspaceEntity?>


    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWorkspaces(workspaces: List<WorkspaceEntity>)
    @Query("""
        SELECT COUNT(*) FROM ${SeugiTable.WORKSPACE_TABLE}
        WHERE workspaceId = :workspaceId OR workspaceName = :workspaceName
    """)
    suspend fun countExistingWorkspaceByIdOrName(workspaceId: String, workspaceName: String): Int

    @Query("DELETE FROM ${SeugiTable.WORKSPACE_TABLE}")
    suspend fun deleteWorkspace()
}