package com.seugi.data.firebase_token.mapper

import com.seugi.data.firebase_token.model.FirebaseTokenModel
import com.seugi.local.room.model.FirebaseTokenEntity

fun FirebaseTokenEntity?.toModel(): FirebaseTokenModel = FirebaseTokenModel(
    firebaseToken = this?.firebaseToken
)