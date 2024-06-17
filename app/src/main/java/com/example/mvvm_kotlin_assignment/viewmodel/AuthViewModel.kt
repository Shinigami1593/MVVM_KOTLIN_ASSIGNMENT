package com.example.mvvm_kotlin_assignment.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm_kotlin_assignment.repository.AuthRepository

class AuthViewModel(val repository: AuthRepository):ViewModel() {
    fun login(username: String, password: String, callback: (Boolean, String?) -> Unit) {
        return repository.login(username, password,callback)
    }

    fun register(username: String, password: String, callback: (Boolean, String?) -> Unit) {
        return repository.register(username, password,callback)
    }
}