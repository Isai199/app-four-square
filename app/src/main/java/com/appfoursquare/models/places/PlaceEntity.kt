package com.appfoursquare.models.places

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.appfoursquare.databaseroom.TABLE_PLACES

@Entity(tableName = TABLE_PLACES)
data class PlaceEntity(
    @PrimaryKey val place_id: String,
    @ColumnInfo(name = "place_photo")   val place_photo: String,
    @ColumnInfo(name = "place_description") val place_description: String,
    @ColumnInfo(name = "place_adress")   val place_adress: String,
    @ColumnInfo(name = "place_status") val place_status: String,
    @ColumnInfo(name = "place_author") val place_author: String,
    @ColumnInfo(name = "place_iduser") val place_iduser: String,
)


fun PlaceEntity.toPlace()  = Place(
    place_id,
    place_photo,
    place_description,
    place_adress,
    place_status,
    place_author,
    place_iduser
)
