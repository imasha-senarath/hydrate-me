package com.imasha.hydrateme.data.enums

enum class Gender {
    MALE, FEMALE, UNSPECIFIED
}

fun Gender.getName(): String {
    return name.lowercase().replaceFirstChar { it.uppercase() }
}