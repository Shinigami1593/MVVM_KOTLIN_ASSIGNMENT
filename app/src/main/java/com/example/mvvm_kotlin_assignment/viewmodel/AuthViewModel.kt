package com.example.mvvm_kotlin_assignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm_kotlin_assignment.repository.AuthRepository

class AuthViewModel(val repository: AuthRepository):ViewModel() {
    fun login(email: String, password: String): LiveData<Boolean> {
        return repository.login(email, password)
    }

    fun register(email: String, password: String): LiveData<Boolean> {
        return repository.register(email, password)
    }
}