package com.andres.nyuzuk.data.datasource.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andres.nyuzuk.data.entity.local.ArticleEntity
import com.andres.nyuzuk.data.entity.local.BasePublisherEntity

@Database(entities = [ArticleEntity::class, BasePublisherEntity::class], version = 1)
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