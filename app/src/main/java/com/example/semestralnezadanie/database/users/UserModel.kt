package com.example.semestralnezadanie.database.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.semestralnezadanie.entities.User

@Entity(tableName = "users")
class UsersDatabase(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    @ColumnInfo(name = "username")
    val username : String?,
    @ColumnInfo(name = "password")
    val password : String,
    @ColumnInfo(name = "access_token")
    val accessToken : String,
    @ColumnInfo(name = "refresh_token")
    val refreshToken : String
){
    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UsersDatabase

        if (id != other.id) return false
        if (username != other.username) return false
        if (password != other.password) return false
        if (accessToken != other.accessToken) return false
        if (refreshToken != other.refreshToken) return false

        return true
    }

    override fun hashCode(): Int
    {
        var result = id.hashCode()
        result = 31 * result + (username?.hashCode() ?: 0)
        result = 31 * result + password.hashCode()
        result = 31 * result + accessToken.hashCode()
        result = 31 * result + refreshToken.hashCode()
        return result
    }

    override fun toString(): String
    {
        return "UsersDatabase(id=$id, username=$username, password='$password', accessToken='$accessToken', refreshToken='$refreshToken')"
    }


}
