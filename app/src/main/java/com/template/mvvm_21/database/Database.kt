package com.template.mvvm_21.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.template.mvvm_21.model.Task

/**
 * Author: Dzhaparov Bekmamat
 */
@Database(entities = [Task::class], version = 1)
abstract class Database : RoomDatabase() {
    abstract fun dao(): Dao
}
