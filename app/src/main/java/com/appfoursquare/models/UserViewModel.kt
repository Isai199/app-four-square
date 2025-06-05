package com.appfoursquare.models


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appfoursquare.databaseroom.DataBaseManager
import kotlinx.coroutines.launch

class UserViewModel: ViewModel() {

    fun saveUser(user: User){
        viewModelScope.launch{
            val userDao = DataBaseManager.instance.database.userDao()
            MyCoroutinesUser(userDao).save(user)
        }
    }


    fun deleteUser(user: User){
        viewModelScope.launch{
            val userDao = DataBaseManager.instance.database.userDao()
            MyCoroutinesUser(userDao).delete(user)
        }
    }


    val savedUsers = MutableLiveData<List<User>>()

    val savedUser = MutableLiveData<User>()

    fun getUsers(){
        viewModelScope.launch{
            val userDao = DataBaseManager.instance.database.userDao()
            savedUsers.value = MyCoroutinesUser(userDao).getUser().value
        }
    }

    fun getUser(id: String){
        viewModelScope.launch{
            val userDao = DataBaseManager.instance.database.userDao()
            savedUser.value = MyCoroutinesUser(userDao).getOnlyUser(id).value
        }
    }

}