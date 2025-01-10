package com.imasha.hydrateme.data.model

import com.imasha.hydrateme.data.enums.Gender

data class User(
    var id: String = "",
    var name: String = "",
    var email: String = "",
    var weight: Double = 0.0,
    var gender: Gender = Gender.UNSPECIFIED,
    var wakeUpTime: String = "",
    var bedTime: String = "",
    val password: String = ""
)