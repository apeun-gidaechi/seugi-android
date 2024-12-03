package com.seugi.local.room.dao

import androidx.room.Dao
import androidx.room.Query
import com.seugi.local.room.base.BaseDao
import com.seugi.local.room.entity.WorkspaceEntity
import com.seugi.local.room.entity.WorkspaceNotificationEntity
import com.seugi.local.room.util.SeugiTable

@Dao
interface WorkspaceNotificationDao : BaseDao<WorkspaceNotificationEntity> {

    @Query("SELECT * FROM ${SeugiTable.WORKSPACE_NOTIFICATION_TABLE} WHERE workspaceId = :workspaceId")
    suspend fun getWorkspaceByWorkspaceId(workspaceId: String): WorkspaceNotificationEntity?

    @Query("UPDATE ${SeugiTable.WORKSPACE_NOTIFICATION_TABLE} SET isReceiveFCM = :isReceiveFCM WHERE workspaceId = :workspaceId")
    suspend fun updateIsReceiveFCMByWorkspaceId(isReceiveFCM: Boolean, workspaceId: String)

    @Query("DELETE FROM ${SeugiTable.WORKSPACE_NOTIFICATION_TABLE}")
    suspend fun deleteWorkspace()
}
