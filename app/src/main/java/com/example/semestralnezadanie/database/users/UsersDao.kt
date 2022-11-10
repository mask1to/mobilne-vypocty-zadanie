package com.example.semestralnezadanie.database.users

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao
{
    @Query("SELECT * FROM users WHERE id = :id")
    fun getUser(id: Long): Flow<UsersDatabase>

    @Query("SELECT * FROM users ORDER BY username ASC")
    fun getAllUsersAsc(): Flow<List<UsersDatabase>>

    @Query("SELECT * FROM users ORDER BY username DESC")
    fun getAllUsersDesc(): Flow<List<UsersDatabase>>

}