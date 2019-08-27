package com.andres.nyuzuk.data.datasource.local.database

import androidx.room.TypeConverter
import com.andres.nyuzuk.data.entity.local.PublisherEntity
import com.andres.nyuzuk.data.tools.jsonserializer.JsonSerializer
import com.andres.nyuzuk.data.tools.jsonserializer.MoshiJsonSerializer
import com.squareup.moshi.Moshi

class Converters {
    private val jsonSerializer: JsonSerializer = MoshiJsonSerializer(Moshi.Builder().build())

    @TypeConverter
    fun publisherEntityToJson(publisherEntity: PublisherEntity?): String? {
        return if (publisherEntity == null) {
            ""
        } else {
            jsonSerializer.toJson(publisherEntity)
        }
    }

    @TypeConverter
    fun publisherEntityFromJson(json: String): PublisherEntity? {
        return if (json.isEmpty()) {
            null
        } else {
            jsonSerializer.fromJson(json, PublisherEntity::class.java)
        }
    }
}