package com.example.foodme

import android.app.Application
import com.example.foodme.data.source.local.database.AppDatabase

class App : Application(){
    override fun onCreate() {
        super.onCreate()
        AppDatabase.getInstance(this)
    }
}