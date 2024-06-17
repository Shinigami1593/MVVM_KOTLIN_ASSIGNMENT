package com.example.mvvm_kotlin_assignment.repository

import androidx.lifecycle.LiveData

interface AuthRepository {
    fun login(username: String, password: String, callback: (Boolean, String?) -> Unit)

    fun register(username: String, password: String, callback: (Boolean, String?) -> Unit)
}