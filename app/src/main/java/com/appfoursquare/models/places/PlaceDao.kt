package com.appfoursquare.models.places

import androidx.room.*
import com.appfoursquare.databaseroom.TABLE_PLACES

@Dao
interface PlaceDao {
    @Query("SELECT * FROM $TABLE_PLACES")
    fun getPlaceFromdatabase(): List<PlaceEntity>

    @Query("SELECT * FROM $TABLE_PLACES where Place_id = :uuid")
    fun getPlaceByUuid(uuid: String): PlaceEntity

    @Delete
    fun deletePlace(place: PlaceEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun savePlace(place: PlaceEntity)

    @Query("UPDATE $TABLE_PLACES SET Place_status = 'enabled' WHERE Place_id = :uuid")
    fun updatePlace(uuid: String)
}