package com.andres.nyuzuk.data.entity.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "publisher")
data class PublisherEntity(
    @PrimaryKey(autoGenerate = true) val _id: Int = 0,
    @ColumnInfo(name = "id") val id: String?,
    @ColumnInfo(name = "name") val name: String
)