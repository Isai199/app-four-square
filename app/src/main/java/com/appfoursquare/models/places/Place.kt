package com.appfoursquare.models.places

import java.io.Serializable

class Place(
    place_id: String,
    place_photo: String,
    place_description: String,
    place_title: String,
    place_status: String,
    place_author: String,
    place_iduser: String
): Serializable {
    val place_id : String = place_id
    val place_photo: String= place_photo
    val place_description: String = place_description
    val place_title : String = place_title
    val place_status: String= place_status
    val place_author: String = place_author
    val place_iduser: String = place_iduser
}


fun Place.toEntity() = PlaceEntity(
    place_id,
    place_photo,
    place_description,
    place_title,
    place_status,
    place_author,
    place_iduser

)