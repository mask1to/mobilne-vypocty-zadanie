package com.example.semestralnezadanie.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.semestralnezadanie.database.friends.FriendsModel
import com.example.semestralnezadanie.database.pubs.PubsModel


@Database(entities = [PubsModel::class, FriendsModel::class], version = 1, exportSchema = false)
abstract class DatabasePubs : RoomDatabase()
{
    abstract fun pubsDao(): DatabasePubsDao

    companion object {
        @Volatile
        private var INSTANCE: DatabasePubs? = null

        fun getDatabase(context: Context): DatabasePubs
        {
            return INSTANCE ?: synchronized(this)
            {
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