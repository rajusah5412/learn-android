package com.example.todo

import android.app.Application
import com.example.todo.data.AppDatabase

class MyApplication : Application() {


    companion object{
        lateinit var database: AppDatabase
    }
    override fun onCreate() {
        super.onCreate()
        database = AppDatabase.getInstance(this)

    }
}