package com.imasha.hydrateme.data.source

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.imasha.hydrateme.data.model.User
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class FirebaseSource(private val firebaseAuth: FirebaseAuth) {

    suspend fun userAuthentication(): Boolean = suspendCoroutine { continuation ->
        val user = firebaseAuth.currentUser
        continuation.resume(user != null)
    }

    fun getCurrentUserId(): String? {
        return firebaseAuth.currentUser?.uid
    }

    suspend fun login(user: User): Boolean = suspendCoroutine { continuation ->
        firebaseAuth.signInWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(true)
                } else {
                    continuation.resumeWithException(task.exception ?: Exception("Login failed"))
                }
            }
    }

    suspend fun signUp(user: User): Boolean = suspendCoroutine { continuation ->
        firebaseAuth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    continuation.resume(true)
                } else {
                    continuation.resumeWithException(task.exception ?: Exception("Sign up failed"))
                }
            }
    }

    suspend fun saveData(
        collection: String,
        document: String,
        dataMap: Map<String, Any>,
    ): Boolean = suspendCoroutine { continuation ->

        if (document.isEmpty()) {
            FirebaseFirestore.getInstance().collection(collection).add(dataMap)
                .addOnSuccessListener {
                    continuation.resume(true)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }
        } else {
            FirebaseFirestore.getInstance().collection(collection).document(document).set(dataMap)
                .addOnSuccessListener {
                    continuation.resume(true)
                }
                .addOnFailureListener { exception ->
                    continuation.resumeWithException(exception)
                }

        }
    }

    suspend fun saveUserData(user: User): Boolean = suspendCoroutine { continuation ->
        val userMap = mapOf(
            "name" to user.name,
            "email" to user.email,
        )

        FirebaseFirestore.getInstance().collection("Users").document(user.id).set(userMap)
            .addOnSuccessListener {
                continuation.resume(true)
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }

    fun logout(): Boolean {
        return try {
            firebaseAuth.signOut()
            true
        } catch (exception: Exception) {
            false
        }
    }
}