package com.imasha.hydrateme.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.imasha.hydrateme.data.enums.Gender

@Entity(tableName = "user")
data class User(
    @PrimaryKey
    var id: String = "",
    var name: String = "",
    var email: String = "",
    var weight: Double = 0.0,
    var gender: Gender = Gender.UNSPECIFIED,
    var goal: Int = 0,
    var wakeUpTime: String = "",
    var bedTime: String = "",
    val password: String = ""
)