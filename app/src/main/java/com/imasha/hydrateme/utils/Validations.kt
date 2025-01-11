package com.imasha.hydrateme.utils

object Validations {

    fun Any.isAnyNotEmpty(): Boolean {
        return when (this) {
            is String -> this.isNotEmpty()
            is Double -> this != 0.0
            else -> false
        }
    }
}