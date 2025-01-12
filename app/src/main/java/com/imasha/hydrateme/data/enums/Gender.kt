package com.imasha.hydrateme.data.enums

enum class Gender(private val displayName: String) {
    MALE("Male"),
    FEMALE("Female"),
    UNSPECIFIED("Unspecified");

    override fun toString(): String {
        return displayName
    }
}