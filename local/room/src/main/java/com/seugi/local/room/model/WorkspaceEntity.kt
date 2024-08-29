package com.seugi.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seugi.local.room.util.SeugiTable

@Entity(tableName = SeugiTable.WORKSPACE_TABLE)
data class WorkspaceEntity(
    @PrimaryKey
    var idx: Long = 0,
    val workspaceId: String,
)
