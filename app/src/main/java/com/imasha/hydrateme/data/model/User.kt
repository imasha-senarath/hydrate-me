package com.imasha.hydrateme.data.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val weight: Double,
    val gender: String,
    val wakeUpTime: String,
    val BedTime: String,
    val password: String
) {
    constructor(email: String, password: String) : this ("", "", email, 0.0, "", "", "", password)
}