package com.andres.nyuzuk.data.datasource.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.andres.nyuzuk.data.entity.local.ArticleEntity
import com.andres.nyuzuk.data.entity.local.PublisherEntity

@Database(entities = [ArticleEntity::class, PublisherEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun ArticleDao(): ArticleDao

    companion object {
        private val DATABASE_NAME = "article_db"
        @Volatile
        private var INSTANCE: ArticleDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = INSTANCE ?: synchronized(LOCK) {
            INSTANCE ?: createDatabase(context).also { INSTANCE = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context, ArticleDatabase::class.java, DATABASE_NAME).build()
    }
}