package com.andres.nyuzuk.data.datasource.local.database

import androidx.room.TypeConverter
import com.andres.nyuzuk.data.entity.local.BasePublisherEntity
import com.andres.nyuzuk.data.tools.jsonserializer.JsonSerializer
import com.andres.nyuzuk.data.tools.jsonserializer.MoshiJsonSerializer
import com.squareup.moshi.Moshi

class Converters {
    private val jsonSerializer: JsonSerializer = MoshiJsonSerializer(Moshi.Builder().build())

    @TypeConverter
    fun basePublisherEntityToJson(basePublisherEntity: BasePublisherEntity?): String? {
        return if (basePublisherEntity == null) {
            ""
        } else {
            jsonSerializer.toJson(basePublisherEntity)
        }
    }

    @TypeConverter
    fun basePublisherEntityFromJson(json: String): BasePublisherEntity? =
        jsonSerializer.fromJson(json, BasePublisherEntity::class.java)
}