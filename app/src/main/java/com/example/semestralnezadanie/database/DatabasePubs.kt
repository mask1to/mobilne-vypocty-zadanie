package com.example.semestralnezadanie.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.semestralnezadanie.database.pubs.PubsDao
import com.example.semestralnezadanie.database.pubs.PubsDatabase


@Database(entities = [PubsDatabase::class], version = 2, exportSchema = false)
abstract class DatabasePubs : RoomDatabase()
{
    abstract fun pubsDao(): PubsDao

    companion object {
        @Volatile
        private var INSTANCE: DatabasePubs? = null
        fun getDatabase(context: Context): DatabasePubs {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabasePubs::class.java,
                    "pubs_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}