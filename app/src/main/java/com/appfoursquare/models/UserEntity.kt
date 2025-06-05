package com.appfoursquare.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.appfoursquare.databaseroom.TABLE_USERS

@Entity(tableName = TABLE_USERS)
data class UserEntity(
    // on below line we are specifying our key and
    // then auto generate as true and we are
    // specifying its initial value as 0
    @PrimaryKey val user_id: String,
    @ColumnInfo(name = "user_name")   val user_name: String,
    @ColumnInfo(name = "user_email") val user_email: String,
    @ColumnInfo(name = "user_password")   val user_password: String,
    @ColumnInfo(name = "user_type") val user_type: String,
    @ColumnInfo(name = "user_status") val user_status: String,
)

fun UserEntity.toUser()  = User(
    user_id,
    user_name,
    user_email,
    user_password,
    user_type,
    user_status
)
