package com.seugi.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.seugi.local.room.base.BaseDao
import com.seugi.local.room.model.WorkspaceEntity
import com.seugi.local.room.util.SeugiTable

@Dao
interface WorkspaceDao : BaseDao<WorkspaceEntity> {
    @Query("SELECT * FROM ${SeugiTable.WORKSPACE_TABLE}")
    suspend fun getWorkspaceId(): WorkspaceEntity?

    @Query("UPDATE ${SeugiTable.WORKSPACE_TABLE} SET workspaceId = :newWorkspaceId WHERE idx = :idx")
    suspend fun updateWorkspaceIdById(idx: Long, newWorkspaceId: String)

    @Query("DELETE FROM ${SeugiTable.WORKSPACE_TABLE}")
    suspend fun deleteWorkspace()
}
