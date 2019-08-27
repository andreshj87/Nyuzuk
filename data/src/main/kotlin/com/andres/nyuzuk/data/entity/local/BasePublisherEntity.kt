package com.andres.nyuzuk.data.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "basePublisher")
data class BasePublisherEntity(
    @PrimaryKey(autoGenerate = true) val dbId: Int = 0,
    @ColumnInfo(name = "id") val id: String?,
    @ColumnInfo(name = "name") val name: String
)