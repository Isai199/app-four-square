package com.appfoursquare.models.opinions

import java.io.Serializable

class Opinion(
    opinion_id: String,
    opinion_comment: String,
    opinion_iduer: String,
    opinion_idplace: String

):Serializable {
    val opinion_id : String = opinion_id
    val opinion_comment: String = opinion_comment
    val opinion_iduer : String = opinion_iduer
    val opinion_idplace: String= opinion_idplace
}

fun Opinion.toEntity() = OpinionEntity(
    opinion_id,
    opinion_comment,
    opinion_iduer,
    opinion_idplace
)

data class OpinionWithUser(
    val opinion_comment: String,
    val opinion_idplace: String,
    val user_name: String
)