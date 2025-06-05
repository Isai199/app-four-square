package com.appfoursquare.databaseroom

import androidx.room.Database
import androidx.room.RoomDatabase
import com.appfoursquare.models.UserDao
import com.appfoursquare.models.UserEntity
import com.appfoursquare.models.opinions.OpinionDao
import com.appfoursquare.models.opinions.OpinionEntity
import com.appfoursquare.models.places.PlaceDao
import com.appfoursquare.models.places.PlaceEntity

const val DATABASE_VERSION = 8
const val TABLE_USERS = "users"
const val TABLE_PLACES = "places"
const val TABLE_OPINIONS = "opinions"
const val DATABASE_NAME = "databasefoursquare.sqlite"

@Database(entities = [UserEntity::class , PlaceEntity::class, OpinionEntity::class],
    version = DATABASE_VERSION
)
abstract class AppDataBase : RoomDatabase(){
    abstract fun userDao(): UserDao

    abstract fun placeDao(): PlaceDao

    abstract fun opinionDao(): OpinionDao
}