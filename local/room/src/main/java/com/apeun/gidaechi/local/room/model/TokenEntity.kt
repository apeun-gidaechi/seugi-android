package com.apeun.gidaechi.local.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.apeun.gidaechi.local.room.util.SeugiTable

@Entity(tableName = SeugiTable.TOKEN_TABLE)
data class TokenEntity(
    @PrimaryKey val idx: Int,
    val token: String,
    val refreshToken: String
) {
    constructor(token: String, refreshToken: String) : this(0, token, refreshToken)
}