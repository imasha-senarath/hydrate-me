package com.imasha.hydrateme.data.enums

enum class Theme(private val displayName: String) {
    LIGHT("Light"),
    DARK("Dark"),
    SYSTEM("System");

    override fun toString(): String {
        return displayName
    }
}