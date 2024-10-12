package com.seugi.network.core.utiles
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class LocalDateTimeTypeAdapter : JsonDeserializer<LocalDateTime>, JsonSerializer<LocalDateTime> {
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): LocalDateTime {
        var dateString = json?.asString
        if (dateString?.startsWith("0000") == true) {
            return LocalDateTime.MIN
        }
        val dateStrings = dateString!!.split(".")
        if (dateStrings[1].length != 6) {
            dateString = dateStrings[0] + "." + dateStrings[1] + "0".repeat(if (0 > 6 - dateStrings[1].length) 0 else 6 - dateStrings[1].length)
        }
        return LocalDateTime.parse(dateString.substring(startIndex = 0, endIndex = 26), formatter)
    }

    override fun serialize(src: LocalDateTime?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
        return JsonPrimitive(src?.minusHours(9)?.format(formatter))
    }
}
