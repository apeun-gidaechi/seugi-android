package com.seugi.network.core.utiles

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.headers
import java.time.LocalDateTime

inline fun <reified T> String.toResponse(): T {
    return GsonConverter.gson.fromJson(this, T::class.java)
}

inline fun <reified T> String.toResponse(type: Class<T>): T {
    return GsonConverter.gson.fromJson(this, type)
}

/**
 * Data Class To Json String
 *
 * @param T Only data class
 * @return String
 */
inline fun <reified T> T.toJsonString(): String {
    if (T::class.isData) {
        return GsonConverter.gson.toJson(this)
    } else {
        throw IllegalArgumentException("Only data classes are allowed")
    }
}

fun HttpRequestBuilder.addTestHeader(token: String) = this.headers {
    append("Authorization", token)
}

object GsonConverter {

    val gson: Gson by lazy {
        GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, LocalDateTimeTypeAdapter())
            .setPrettyPrinting()
            .setLenient()
            .create()
    }
}

fun removeBearer(token: String): String {
    return if (token.startsWith("Bearer ")) {
        token.substring(7)
    } else {
        token
    }
}
