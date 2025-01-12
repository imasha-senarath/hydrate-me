package com.imasha.hydrateme.data.enums

enum class Language(private val displayName: String) {
    ENGLISH("English"),
    SINHALA("සිංහල");

    override fun toString(): String {
        return displayName
    }
}