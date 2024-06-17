package com.example.mvvm_kotlin_assignment.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AuthRepositoryImplementation:AuthRepository{
//    var database:FirebaseDatabase = FirebaseDatabase.getInstance()
    var auth:FirebaseAuth = FirebaseAuth.getInstance()

    override fun login(email: String, password: String): LiveData<Boolean> {
        val registerResult = MutableLiveData<Boolean>()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                registerResult.value = task.isSuccessful
            }
        return registerResult
    }

    override fun register(email: String, password: String): LiveData<Boolean> {
        Tval registerResult = MutableLiveData<Boolean>()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                registerResult.value = task.isSuccessful
            }
        return registerResult
    }

}