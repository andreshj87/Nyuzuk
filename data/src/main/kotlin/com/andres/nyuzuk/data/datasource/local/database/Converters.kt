package com.andres.nyuzuk.data.datasource.local.database

import androidx.room.TypeConverter
import com.andres.nyuzuk.data.entity.local.PublisherEntity
import com.andres.nyuzuk.data.tools.jsonserializer.JsonSerializer
import com.andres.nyuzuk.data.tools.jsonserializer.MoshiJsonSerializer
import com.squareup.moshi.Moshi
import java.util.Date

class Converters {
    internal var jsonSerializer: JsonSerializer = MoshiJsonSerializer(Moshi.Builder().build())

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

    @TypeConverter
    fun toDate(date: Long?): Date? {
        return if (date == null) {
            null
        } else {
            Date(date)
        }
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time
}