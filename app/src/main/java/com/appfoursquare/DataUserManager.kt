package com.appfoursquare

import android.content.Context
import android.content.Context.MODE_PRIVATE
import com.appfoursquare.models.User

class DataUserManager(context: Context) {

    val context: Context = context

    fun getUUID(): String {
        return java.util.UUID.randomUUID().toString()
    }

    fun savePrefUserData(user: User) {
        val sharedPref = this.context.getSharedPreferences("user_info", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("user_id", user.user_id)
        editor.putString("user_name", user.user_name)
        editor.putString("user_type", user.user_type)
        editor.apply()
    }

    fun getPrefUserData(): ArrayList<String> {
        val sharedPref = this.context.getSharedPreferences("user_info", MODE_PRIVATE)
        val userId = sharedPref.getString("user_id", "").toString()
        val userName = sharedPref.getString("user_name", "").toString()
        val userType = sharedPref.getString("user_type", "").toString()
        val userdataList: ArrayList<String> = arrayListOf(userId, userName, userType)

        return userdataList
    }

    fun clearShardPreferences() {
        val sharedPref = this.context.getSharedPreferences("user_info", MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }
}