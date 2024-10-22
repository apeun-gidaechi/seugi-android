package com.seugi.network.core.utiles

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

class ImmutableListTypeAdapter<T> : TypeAdapter<ImmutableList<T>>() {
    override fun write(out: JsonWriter, value: ImmutableList<T>?) {
        if (value == null) {
            out.nullValue()
        } else {
            out.beginArray()
            for (item in value) {
                out.jsonValue(GsonConverter.gson.toJson(item))
            }
            out.endArray()
        }
    }

    override fun read(`in`: JsonReader): ImmutableList<T> {
        val list = mutableListOf<T>()
        `in`.beginArray()
        while (`in`.hasNext()) {
            val item: T = GsonConverter.gson.fromJson(`in`, Any::class.java) as T
            list.add(item)
        }
        `in`.endArray()
        return list.toImmutableList()
    }
}
