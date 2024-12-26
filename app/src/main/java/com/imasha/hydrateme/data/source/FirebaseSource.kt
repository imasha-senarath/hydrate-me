package com.imasha.hydrateme.data.source

import com.google.firebase.auth.FirebaseAuth
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseSource(private val firebaseAuth: FirebaseAuth) {

    suspend fun userAuthentication(): Boolean = suspendCoroutine { continuation ->
        val user = firebaseAuth.currentUser
        continuation.resume(user != null)
    }

    suspend fun login(email: String, password: String): Boolean = suspendCoroutine { continuation ->
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(true)
                } else {
                    continuation.resumeWithException(task.exception ?: Exception("Login failed"))
                }
            }
    }
}