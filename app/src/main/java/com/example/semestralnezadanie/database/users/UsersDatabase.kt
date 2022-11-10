package com.example.semestralnezadanie.database.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UsersDatabase(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    @ColumnInfo(name = "username")
    val username : String?,
    @ColumnInfo(name = "email")
    val email : String,
    @ColumnInfo(name = "password")
    val password : String,
)
