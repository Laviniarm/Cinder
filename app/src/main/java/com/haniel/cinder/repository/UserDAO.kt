package com.haniel.cinder.repository;

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.haniel.cinder.model.User


class UserDAO {
    val db = FirebaseFirestore.getInstance()

    fun find(callback: (List<User>) -> Unit) {
        db.collection("users").get()
            .addOnSuccessListener { document ->
                val users = document.toObjects<User>()
                callback(users)
            }
            .addOnFailureListener {
                callback(emptyList())
            }
    }

    fun findByName(name: String, callback: (User?) -> Unit) {
        db.collection("users").whereEqualTo("name", name).get()
            .addOnSuccessListener { document ->
                if (!document.isEmpty) {
                    val user = document.documents[0].toObject<User>()
                    if (user != null) {
                        callback(user)
                    }

                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                callback(null)
            }
    }

    fun findbyId(id: String, callback: (User?) -> Unit) {
        db.collection("users").whereEqualTo("id", id).get()
            .addOnSuccessListener { document ->
                if (!document.isEmpty) {
                    val user = document.documents[0].toObject<User>()
                    if (user != null) {
                        callback(user)
                    }
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener {
                callback(null)
            }
    }


    fun add(user: User, callback: (User?) -> Unit) {
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                user.id = documentReference.id
                callback(user)
            }
            .addOnFailureListener { e ->
                callback(null)
            }
    }

}