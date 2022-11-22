package com.example.semestralnezadanie.database.pubs

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pubs")
class PubsModel(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    @ColumnInfo(name = "name")
    val name : String?,
    @ColumnInfo(name = "type")
    val type : String,
    @ColumnInfo(name = "firm_owner")
    val owner : String?,
    @ColumnInfo(name = "latitude")
    val latitude : String,
    @ColumnInfo(name = "longitude")
    val longitude : String,
    @ColumnInfo(name = "website")
    val website : String?,
    @ColumnInfo(name = "phone_contact")
    val phoneContact : String?,
    @ColumnInfo(name = "users_count")
    val users : Int,
)
{

    override fun equals(other: Any?): Boolean
    {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PubsModel

        if (id != other.id) return false
        if (name != other.name) return false
        if (type != other.type) return false
        if (owner != other.owner) return false
        if (latitude != other.latitude) return false
        if (longitude != other.longitude) return false
        if (website != other.website) return false
        if (phoneContact != other.phoneContact) return false

        return true
    }

    override fun hashCode(): Int
    {
        var result = id.hashCode()
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + type.hashCode()
        result = 31 * result + (owner?.hashCode() ?: 0)
        result = 31 * result + latitude.hashCode()
        result = 31 * result + longitude.hashCode()
        result = 31 * result + (website?.hashCode() ?: 0)
        result = 31 * result + (phoneContact?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String
    {
        return "PubsDatabase(id=$id, name=$name, type='$type', owner=$owner, latitude='$latitude', longitude='$longitude', website=$website, phoneContact=$phoneContact)"
    }
}


