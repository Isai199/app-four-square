package com.appfoursquare.models.opinions

import androidx.room.*
import com.appfoursquare.databaseroom.TABLE_OPINIONS
import com.appfoursquare.databaseroom.TABLE_USERS

@Dao
interface OpinionDao {
    @Query("SELECT * FROM $TABLE_OPINIONS")
    fun getOpinionFromdatabase(): List<OpinionEntity>

    @Query("SELECT * FROM $TABLE_OPINIONS where opinion_id = :uuid")
    fun getOpinionByUuid(uuid: String): OpinionEntity

    @Delete
    fun deleteOpinion(opinion: OpinionEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveOpinion(opinion: OpinionEntity)

    @Query("""
        SELECT o.opinion_comment, o.opinion_idplace, u.user_name 
        FROM $TABLE_OPINIONS o 
        INNER JOIN $TABLE_USERS u 
        ON o.opinion_iduer = u.user_id WHERE o.opinion_idplace = :uuid
        """)
    fun getOpinionAndUser(uuid: String): List<OpinionWithUser>

}