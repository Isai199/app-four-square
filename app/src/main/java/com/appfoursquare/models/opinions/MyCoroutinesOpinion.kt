package com.appfoursquare.models.opinions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyCoroutinesOpinion (private val opinionDao: OpinionDao) {
    suspend fun save(opinion: Opinion) = withContext(Dispatchers.IO){
        opinionDao.saveOpinion(opinion.toEntity())
    }

    suspend fun delete(opinion: Opinion) = withContext(Dispatchers.IO){
        opinionDao.deleteOpinion(opinion.toEntity())
    }

    suspend fun getOpinion(): LiveData<List<Opinion>> = withContext(Dispatchers.IO){
        return@withContext MutableLiveData(opinionDao.getOpinionFromdatabase().map {eachOpinionEntity ->
            eachOpinionEntity.toOpinion() })
    }

    suspend fun getOpinionAndUser(uuid: String): LiveData<List<OpinionWithUser>> = withContext(Dispatchers.IO){
        return@withContext MutableLiveData(opinionDao.getOpinionAndUser(uuid))
    }

}