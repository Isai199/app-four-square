package com.appfoursquare.models.opinions

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.appfoursquare.databaseroom.TABLE_OPINIONS

@Entity(tableName = TABLE_OPINIONS)
data class OpinionEntity(

    @PrimaryKey val opinion_id: String,
    @ColumnInfo(name = "opinion_comment") val opinion_comment: String,
    @ColumnInfo(name = "opinion_iduer")   val opinion_iduer: String,
    @ColumnInfo(name = "opinion_idplace") val opinion_idplace: String
)


fun OpinionEntity.toOpinion()  = Opinion(
    opinion_id,
    opinion_comment,
    opinion_iduer,
    opinion_idplace
)
