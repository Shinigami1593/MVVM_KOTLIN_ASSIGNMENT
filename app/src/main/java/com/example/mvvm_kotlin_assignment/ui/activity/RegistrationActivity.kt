package com.example.mvvm_kotlin_assignment.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.mvvm_kotlin_assignment.R
import com.example.mvvm_kotlin_assignment.databinding.ActivityRegistrationBinding
import com.example.mvvm_kotlin_assignment.viewmodel.AuthViewModel
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity() {
    lateinit var binding:ActivityRegistrationBinding
    lateinit var authViewModel: AuthViewModel
    var firebaseDatabase:FirebaseDatabase = FirebaseDatabase.getInstance()
    var ref = firebaseDatabase.reference.child("user")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding =ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.reg.setOnClickListener {
            val email = binding.regUsername.text.toString().trim()
            val password = binding.regPass.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.register(email, password).observe(this, Observer { isSuccess ->
                    if (isSuccess) {
                        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                        // Navigate to the login screen or next screen
                    } else {
                        Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show()
                    }
                })
            } else {
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}