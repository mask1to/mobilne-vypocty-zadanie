package com.example.semestralnezadanie.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.semestralnezadanie.database.friends.FriendsModel
import com.example.semestralnezadanie.database.pubs.PubsModel

@Dao
interface DatabasePubsDao
{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPubs(pubs : List<PubsModel>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFriends(friends : List<FriendsModel>)

    @Query("DELETE FROM pubs")
    suspend fun deletePubs()

    @Query("DELETE FROM friends")
    suspend fun deleteFriends()

    @Query("SELECT * FROM pubs ORDER BY users_count DESC, name ASC")
    fun getPubs() : LiveData<List<PubsModel>?>

    //order pubs by name
    @Query("SELECT * FROM pubs WHERE users_count >= 1 ORDER BY name ASC")
    fun sortPubs() : LiveData<List<PubsModel>?>

    @Query("SELECT * FROM friends WHERE custom_id = :id")
    fun getFriends(id : Long) : LiveData<List<FriendsModel>?>
}