package com.apeun.gidaechi.network.core.utiles

import com.google.gson.Gson

inline fun <reified T> String.toResponse(): T {
    return Gson().fromJson(this, T::class.java)
}

inline fun <reified T> String.toResponse(type: Class<T>): T {
    return Gson().fromJson(this, type)
}