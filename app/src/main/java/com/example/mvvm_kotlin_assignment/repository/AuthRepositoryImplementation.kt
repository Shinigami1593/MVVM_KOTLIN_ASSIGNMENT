package com.example.mvvm_kotlin_assignment.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AuthRepositoryImplementation:AuthRepository{
    var database:FirebaseDatabase = FirebaseDatabase.getInstance()
//    var ref = database.reference.child("user")
    var auth:FirebaseAuth = FirebaseAuth.getInstance()

    override fun login(username: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.signInWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

    override fun register(username: String, password: String, callback: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true, null)
                } else {
                    callback(false, task.exception?.message)
                }
            }
    }

}