package com.imasha.hydrateme.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "record")
data class Record(
    @PrimaryKey
    val id: String = "",
    val user: String = "",
    val size: Int = 0,
    val time: String = "",
    val date: String = ""
)