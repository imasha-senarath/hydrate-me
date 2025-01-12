package com.imasha.hydrateme.data.enums

enum class Theme(private val displayName: String) {
    LIGHT("Light Theme"),
    DARK("Dark Theme"),
    SYSTEM("System Theme");

    override fun toString(): String {
        return displayName
    }
}