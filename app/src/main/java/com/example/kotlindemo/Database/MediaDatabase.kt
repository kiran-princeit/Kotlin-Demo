package com.example.kotlindemo.Database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract  class MediaDatabase: RoomDatabase() {
    abstract fun mediaItemDao(): MediaItemDao

    companion object {
        @Volatile
        private var INSTANCE: MediaDatabase? = null

        fun getDatabase(context: Context): MediaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MediaDatabase::class.java,
                    "media_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}