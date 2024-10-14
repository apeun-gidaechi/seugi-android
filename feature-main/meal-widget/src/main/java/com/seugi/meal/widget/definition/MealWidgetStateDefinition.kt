package com.seugi.meal.widget.definition

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.glance.state.GlanceStateDefinition
import com.seugi.meal.widget.model.MealWidgetState
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json

object MealWidgetStateDefinition : GlanceStateDefinition<MealWidgetState> {

    private const val DATA_STORE_FILENAME = "mealState"
    private val Context.dataStore by dataStore(DATA_STORE_FILENAME, MealWidgetStateSerializer)

    private val LIST_KEY = stringSetPreferencesKey("list_key")

    override suspend fun getDataStore(context: Context, fileKey: String): DataStore<MealWidgetState> {
        return context.dataStore
    }

    override fun getLocation(context: Context, fileKey: String): File {
        return context.dataStoreFile(DATA_STORE_FILENAME)
    }
}

object MealWidgetStateSerializer : Serializer<MealWidgetState> {
    override val defaultValue = MealWidgetState.Loading

    override suspend fun readFrom(input: InputStream): MealWidgetState = try {
        Json.decodeFromString(
            MealWidgetState.serializer(),
            input.readBytes().decodeToString(),
        )
    } catch (exception: SerializationException) {
        throw CorruptionException("Could not read data: ${exception.message}")
    }

    override suspend fun writeTo(t: MealWidgetState, output: OutputStream) {
        output.use {
            it.write(
                Json.encodeToString(MealWidgetState.serializer(), t).encodeToByteArray(),
            )
        }
    }
}
