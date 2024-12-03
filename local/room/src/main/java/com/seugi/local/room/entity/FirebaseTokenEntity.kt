package com.seugi.local.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.seugi.local.room.util.SeugiTable

@Entity(tableName = SeugiTable.FIREBASE_TOKEN_TABLE)
data class FirebaseTokenEntity(
    @PrimaryKey val idx: Int,
    val firebaseToken: String,
) {
    constructor(firebaseToken: String) : this(0, firebaseToken)
}
