package com.appfoursquare.models.places

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appfoursquare.databaseroom.DataBaseManager
import kotlinx.coroutines.launch

class PlaceViewModel: ViewModel() {
    fun savePlace(place: Place){
        viewModelScope.launch{
            val placeDao = DataBaseManager.instance.database.placeDao()
            MyCoroutinesPlace(placeDao).save(place)
        }
    }


    fun deletePlace(place: Place){
        viewModelScope.launch{
            val placeDao = DataBaseManager.instance.database.placeDao()
            MyCoroutinesPlace(placeDao).delete(place)
        }
    }


    val savedPlaces = MutableLiveData<List<Place>>()

    fun getPlaces(){
        viewModelScope.launch{
            val placeDao = DataBaseManager.instance.database.placeDao()
            savedPlaces.value = MyCoroutinesPlace(placeDao).getPlace().value
        }
    }

    fun editPlace(uuid: String){
        viewModelScope.launch{
            val placeDao = DataBaseManager.instance.database.placeDao()
            MyCoroutinesPlace(placeDao).update(uuid)
        }
    }
}