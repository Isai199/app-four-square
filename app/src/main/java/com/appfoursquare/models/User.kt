package com.appfoursquare.models

import java.io.Serializable

class User (

    user_id: String,
    user_name: String,
    user_email: String,
    user_password: String,
    user_type: String,
    user_status: String,

    ): Serializable {

    val user_id: String=user_id
    val user_name: String= user_name
    val user_email: String = user_email
    val user_password: String = user_password
    val user_type: String = user_type
    val user_status: String = user_status
}

fun User.toEntity() = UserEntity(
    user_id,
    user_name,
    user_email,
    user_password,
    user_type,
    user_status
)