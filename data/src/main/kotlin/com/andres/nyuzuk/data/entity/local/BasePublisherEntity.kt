package com.andres.nyuzuk.data.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "basePublisher")
data class BasePublisherEntity(
    @ColumnInfo(name = "id") val id: String?,
    @ColumnInfo(name = "name") val name: String
)