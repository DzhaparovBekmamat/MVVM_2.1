package com.template.mvvm_21.app

/**
 * Author: Dzhaparov Bekmamat
 */
import android.app.Application
import androidx.room.Room
import com.template.mvvm_21.database.Database

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(applicationContext, Database::class.java, "TaskFile")
            .allowMainThreadQueries().build()
    }

    companion object {
        lateinit var database: Database
    }
}
