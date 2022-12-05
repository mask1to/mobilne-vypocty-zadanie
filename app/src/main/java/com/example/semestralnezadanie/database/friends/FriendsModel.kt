package com.example.semestralnezadanie.database.friends

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "friends")
class FriendsModel(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    @ColumnInfo(name = "custom_id")
    val customId : Long,
    @ColumnInfo(name = "user_id")
    val userId: String,
    @ColumnInfo(name = "username")
    val userName: String,
    @ColumnInfo(name = "pub_id")
    val pubId: String?,
    @ColumnInfo(name = "pub_name")
    val pubName: String?,
    @ColumnInfo(name = "time")
    val time: String?,
    @ColumnInfo(name = "pub_latitude")
    var pubLat: Double?,
    @ColumnInfo(name = "pub_longitude")
    var pubLon: Double?
)
{
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FriendsModel

        if (id != other.id) return false
        if (customId != other.customId) return false
        if (userId != other.userId) return false
        if (userName != other.userName) return false
        if (pubId != other.pubId) return false
        if (pubName != other.pubName) return false
        if (time != other.time) return false
        if (pubLat != other.pubLat) return false
        if (pubLon != other.pubLon) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + customId.hashCode()
        result = 31 * result + userId.hashCode()
        result = 31 * result + userName.hashCode()
        result = 31 * result + (pubId?.hashCode() ?: 0)
        result = 31 * result + (pubName?.hashCode() ?: 0)
        result = 31 * result + (time?.hashCode() ?: 0)
        result = 31 * result + (pubLat?.hashCode() ?: 0)
        result = 31 * result + (pubLon?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "FriendsModel(id=$id, customId=$customId, userId='$userId', userName='$userName', pubId=$pubId, pubName=$pubName, time=$time, pubLat=$pubLat, pubLon=$pubLon)"
    }


}