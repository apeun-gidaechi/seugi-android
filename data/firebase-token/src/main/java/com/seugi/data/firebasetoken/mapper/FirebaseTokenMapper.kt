package com.seugi.data.firebasetoken.mapper

import com.seugi.data.firebasetoken.model.FirebaseTokenModel
import com.seugi.local.room.model.FirebaseTokenEntity

fun FirebaseTokenEntity?.toModel(): FirebaseTokenModel = FirebaseTokenModel(
    firebaseToken = this?.firebaseToken,
)
