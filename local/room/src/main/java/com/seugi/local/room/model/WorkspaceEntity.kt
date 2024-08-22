package com.seugi.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seugi.local.room.util.SeugiTable

@Entity(tableName = SeugiTable.WORKSPACE_TABLE)
data class WorkspaceEntity(
    val workspaceId: String,
    val workspaceName: String,
) {
    @PrimaryKey(autoGenerate = true)
    var idx: Long = 0
}
