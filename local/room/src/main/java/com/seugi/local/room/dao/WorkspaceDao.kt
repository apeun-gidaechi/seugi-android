package com.seugi.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.seugi.local.room.base.BaseDao
import com.seugi.local.room.model.WorkspaceEntity
import com.seugi.local.room.util.SeugiTable

@Dao
interface WorkspaceDao : BaseDao<WorkspaceEntity> {
    @Query("SELECT * FROM ${SeugiTable.WORKSPACE_TABLE} WHERE idx = 0")
    suspend fun getWorkspace(): WorkspaceEntity?

    @Query(
        "UPDATE ${SeugiTable.WORKSPACE_TABLE} " +
                "SET workspaceId = :newWorkspaceId," +
                "workspaceName = :workspaceName," +
                "workspaceImageUrl = :workspaceImageUrl," +
                "workspaceAdmin = :workspaceAdmin, " +
                "middleAdmin = :middleAdmin, " +
                "teacher = :teacher, " +
                "student = :student " +
                "WHERE idx = :idx"
    )
    suspend fun updateWorkspaceIdById(
        idx: Long, newWorkspaceId: String,
        workspaceName: String,
        workspaceImageUrl: String,
        workspaceAdmin: Long,
        middleAdmin: List<Long>,
        teacher: List<Long>,
        student: List<Long>
    )

    @Query("DELETE FROM ${SeugiTable.WORKSPACE_TABLE}")
    suspend fun deleteWorkspace()
}
