package com.appfoursquare.databaseroom

import android.app.Application

class MyApplication : Application(){

    @Override
    override fun onCreate() {
        DataBaseManager.instance.initializeDb(applicationContext)
        super.onCreate()
    }
}