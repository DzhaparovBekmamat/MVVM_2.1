package com.template.mvvm_21.model

/**
 * Author: Dzhaparov Bekmamat
 */
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Task")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String? = null,
    val description: String? = null,
    var checkBox: Boolean = false,
) : Serializable