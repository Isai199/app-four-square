package com.appfoursquare.models.opinions

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appfoursquare.databaseroom.DataBaseManager
import kotlinx.coroutines.launch

class OpinionViewModel : ViewModel() {

    fun saveOpinion(opinion: Opinion){
        viewModelScope.launch{
            val opinionDao = DataBaseManager.instance.database.opinionDao()
            MyCoroutinesOpinion(opinionDao).save(opinion)
        }
    }


    fun deleteOpinion(opinion: Opinion){
        viewModelScope.launch{
            val opinionDao = DataBaseManager.instance.database.opinionDao()
            MyCoroutinesOpinion(opinionDao).delete(opinion)
        }
    }


    val savedOpinions = MutableLiveData<List<Opinion>>()
    val savedOpinionAndUser = MutableLiveData<List<OpinionWithUser>>()

    fun getOpinions(){
        viewModelScope.launch{
            val opinionDao = DataBaseManager.instance.database.opinionDao()
            savedOpinions.value = MyCoroutinesOpinion(opinionDao).getOpinion().value
        }
    }

    fun getOpinionAndUser(uuid: String) {
        viewModelScope.launch {
            val opinionDao = DataBaseManager.instance.database.opinionDao()
            savedOpinionAndUser.value = MyCoroutinesOpinion(opinionDao).getOpinionAndUser(uuid).value
        }
    }
}