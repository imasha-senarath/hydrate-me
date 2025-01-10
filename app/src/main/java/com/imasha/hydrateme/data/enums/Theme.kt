package com.imasha.hydrateme.data.enums

enum class Theme {
    LIGHT, DARK, SYSTEM
}

fun Theme.getName(): String {
    return name.lowercase().replaceFirstChar { it.uppercase() }
}