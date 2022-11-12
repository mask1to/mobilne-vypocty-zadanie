package com.example.semestralnezadanie.database.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.semestralnezadanie.entities.User

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

fun UsersDatabase.asDomainModel():User{
    return User(
        id = id,
        username = username,
        email = email,
        password = password
    )
}

fun List<UsersDatabase>.asDomainModel():List<User>{
    return map{
        it.asDomainModel()
    }
}
