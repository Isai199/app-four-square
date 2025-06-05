package com.appfoursquare.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MyCoroutinesUser(private val userDao: UserDao) {
    suspend fun save(user: User) = withContext(Dispatchers.IO){
        userDao.saveUser(user.toEntity())
    }

    suspend fun delete(user: User) = withContext(Dispatchers.IO){
        userDao.deleteUser(user.toEntity())
    }

    suspend fun getUser(): LiveData<List<User>> = withContext(Dispatchers.IO){
        return@withContext MutableLiveData(userDao.getUserFromdatabase().map {eachUserEntity ->
            eachUserEntity.toUser() })
    }

    suspend fun getOnlyUser(id: String): LiveData<User> = withContext(Dispatchers.IO){
        return@withContext MutableLiveData(userDao.getUserByUuid(id).toUser())
    }
}