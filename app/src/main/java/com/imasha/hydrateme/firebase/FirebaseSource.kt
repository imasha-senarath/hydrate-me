package com.imasha.hydrateme.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
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
        val collectionReference = FirebaseFirestore.getInstance().collection(collection)

        val task = if (document.isEmpty()) {
            collectionReference.add(dataMap)
        } else {
            collectionReference.document(document).set(dataMap)
        }

        task.addOnSuccessListener {
            continuation.resume(true)
        }.addOnFailureListener { exception ->
            continuation.resumeWithException(exception)
        }
    }

    suspend fun <T> getData(
        collection: String,
        documentId: String,
        clazz: Class<T>
    ): T = suspendCoroutine { continuation ->
        val documentRef = FirebaseFirestore.getInstance().collection(collection).document(documentId)

        documentRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    try {
                        val data = documentSnapshot.toObject(clazz)
                        if (data != null) {
                            continuation.resume(data)
                        } else {
                            continuation.resumeWithException(Exception("Failed to parse document data"))
                        }
                    } catch (exception: Exception) {
                        continuation.resumeWithException(exception)
                    }
                } else {
                    continuation.resumeWithException(Exception("Data does not exist"))
                }
            }
            .addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }

    suspend fun <T> getDataList(
        collection: String,
        clazz: Class<T>,
        filters: Map<String, Any>
    ): List<T> = suspendCoroutine { continuation ->
        var query: Query = FirebaseFirestore.getInstance().collection(collection)

        for ((field, value) in filters) {
            query = query.whereEqualTo(field, value)
        }

        query.get()
            .addOnSuccessListener { querySnapshot ->
                try {
                    val dataList = querySnapshot.documents.mapNotNull { document ->
                        val dataObject = document.toObject(clazz)
                        dataObject?.apply {
                            this::class.java.getDeclaredField("id").apply {
                                isAccessible = true
                                set(dataObject, document.id)
                            }
                        }
                    }
                    //val dataList = querySnapshot.documents.mapNotNull { it.toObject(clazz) }
                    continuation.resume(dataList)
                } catch (exception: Exception) {
                    continuation.resumeWithException(exception)
                }
            }.addOnFailureListener { exception ->
                continuation.resumeWithException(exception)
            }
    }

    suspend fun deleteData(
        collection: String,
        documentId: String
    ): Boolean = suspendCoroutine { continuation ->
        val documentRef = FirebaseFirestore.getInstance().collection(collection).document(documentId)

        documentRef.delete()
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