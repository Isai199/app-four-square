package com.appfoursquare.models.places

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyCoroutinesPlace(private val placeDao: PlaceDao) {
    suspend fun save(place: Place) = withContext(Dispatchers.IO){
        placeDao.savePlace(place.toEntity())
    }

    suspend fun delete(place: Place) = withContext(Dispatchers.IO){
        placeDao.deletePlace(place.toEntity())
    }

    suspend fun getPlace(): LiveData<List<Place>> = withContext(Dispatchers.IO){
        return@withContext MutableLiveData(placeDao.getPlaceFromdatabase().map {eachPlaceEntity ->
            eachPlaceEntity.toPlace() })
    }

    suspend fun update(uuid: String) = withContext(Dispatchers.IO){
        placeDao.updatePlace(uuid)
    }
}