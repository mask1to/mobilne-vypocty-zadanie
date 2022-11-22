package com.example.semestralnezadanie.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.semestralnezadanie.database.pubs.PubsModel

@Dao
interface DatabasePubsDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPubs(pubs : List<PubsModel>)

    @Query("DELETE FROM pubs")
    suspend fun deletePubs()

    @Query("SELECT * FROM pubs ORDER BY users_count DESC, name ASC")
    fun getPubs() : LiveData<List<PubsModel>?>
}