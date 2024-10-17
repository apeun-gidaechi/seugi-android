package com.seugi.network.core.utiles
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import kotlinx.datetime.LocalDate
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class KotlinLocalDateTypeAdapter : JsonDeserializer<LocalDate>, JsonSerializer<LocalDate> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): LocalDate {
        return LocalDate.parse(json?.asString?: "")
    }

    override fun serialize(src: LocalDate?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive("${src?.year?: 0}-${(src?.monthNumber?: 0).toString().padStart(2, '0')}-${(src?.dayOfMonth?: 0).toString().padStart(2, '0')}-")
    }
}
