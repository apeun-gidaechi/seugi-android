package com.seugi.network.core.utiles
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import com.seugi.network.core.response.FakeLocalDateResponse
import java.lang.reflect.Type
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class FakeLocalDateResponseTypeAdapter : JsonDeserializer<LocalDate>, JsonSerializer<LocalDate> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): LocalDate {
        val dateString = json?.asString?: return LocalDate.now()

        return LocalDate.of(dateString.substring(0, 4).toInt(), dateString.substring(4, 6).toInt(), dateString.substring(6, 8).toInt())
    }

    override fun serialize(src: LocalDate?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src?.toString())
    }
}
