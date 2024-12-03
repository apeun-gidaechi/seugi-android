package com.seugi.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seugi.local.room.util.SeugiTable

@Entity(tableName = SeugiTable.WORKSPACE_NOTIFICATION_TABLE)
data class WorkspaceNotificationEntity(
    @PrimaryKey(autoGenerate = true)
    val idx: Long,
    val workspaceId: String,
    val isReceiveFCM: Boolean
) {
    constructor(
        workspaceId: String,
        isReceiveFCM: Boolean
    ): this(0, workspaceId, isReceiveFCM)
}