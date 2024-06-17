package com.example.mvvm_kotlin_assignment.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mvvm_kotlin_assignment.R
import com.example.mvvm_kotlin_assignment.databinding.ActivityLoginBinding
import com.example.mvvm_kotlin_assignment.model.AuthModel
import com.example.mvvm_kotlin_assignment.repository.AuthRepositoryImplementation
import com.example.mvvm_kotlin_assignment.viewmodel.AuthViewModel

class LoginActivity : AppCompatActivity() {
    lateinit var loginActivity: ActivityLoginBinding
    lateinit var auth:AuthViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        loginActivity = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginActivity.root)

        val repo = AuthRepositoryImplementation()
        auth = AuthViewModel(repo)


//        auth = ViewModelProvider(this).get(AuthViewModel::class.java)

        loginActivity.Login.setOnClickListener{
            val username = loginActivity.username.text.toString()
            val password = loginActivity.password.text.toString()

            Log.d("LoginActivity", "Login button clicked: username=$username, password=$password")
            auth.login(username, password) { success, message ->
                if (success) {
                    navigateToDashboard()
                } else {
//                    Log.e("LoginActivity", "Login failed: $message")
                    Toast.makeText(this,"couldn't find user",Toast.LENGTH_LONG).show()
                }
            }
        }

        loginActivity.reg.setOnClickListener{
            val username = loginActivity.username.text.toString()
            val password = loginActivity.reg.text.toString()
//            var data = AuthModel(username, password)

            Log.d("LoginActivity", "Signup text clicked: username=$username, password=$password")
            auth.register(username,password) { success, message ->
                if (success) {
                    Toast.makeText(this,"Sucessfully Signed up",Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(this,"Unsuccessful signing up",Toast.LENGTH_LONG).show()

                    // Show error message to the user
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
    fun navigateToDashboard(){
        var intent = Intent(this@LoginActivity,DashBoardActivity::class.java)
        startActivity(intent)
    }
}