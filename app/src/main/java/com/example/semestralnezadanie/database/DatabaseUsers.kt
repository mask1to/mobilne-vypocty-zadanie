package com.example.semestralnezadanie.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.semestralnezadanie.database.users.UsersDao
import com.example.semestralnezadanie.database.users.UsersDatabase

@Database(entities = [UsersDatabase::class], version = 2, exportSchema = false)
abstract class DatabaseUsers : RoomDatabase()
{
    abstract fun usersDao(): UsersDao

    companion object {
        @Volatile
        private var INSTANCE: DatabaseUsers? = null
        fun getDatabase(context: Context): DatabaseUsers {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseUsers::class.java,
                    "users_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}