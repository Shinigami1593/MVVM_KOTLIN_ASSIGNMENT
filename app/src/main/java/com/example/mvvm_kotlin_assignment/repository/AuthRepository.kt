package com.example.mvvm_kotlin_assignment.repository

import androidx.lifecycle.LiveData

interface AuthRepository {
    fun login(email: String, password: String, callback: (Boolean, String?) -> Unit)

    fun register(email: String, password: String, callback: (Boolean, String?) -> Unit)
}