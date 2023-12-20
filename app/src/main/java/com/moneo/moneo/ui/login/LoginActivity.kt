package com.moneo.moneo.ui.login

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.moneo.moneo.MainActivity
import com.moneo.moneo.R
import com.moneo.moneo.databinding.ActivityLoginBinding
import com.moneo.moneo.ui.onboarding.OnBoardingActivity
import com.moneo.moneo.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        authStateListener = FirebaseAuth.AuthStateListener { auth ->
            val user = auth.currentUser
            if (user != null) {
                redirectToLoginScreen()
                Log.e(user.uid, "Success Login UID: ${user.uid}")
            }
        }

        setupAction()
        setupView()
        login()
    }

    private fun redirectToLoginScreen(){
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener(authStateListener)
    }


    private fun setupAction() {
        binding.textButtonCreateAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        binding.topAppBar.setOnClickListener {
            startActivity(Intent(this, OnBoardingActivity::class.java))
            finish()
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }


    private fun login(){
        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString().trim()
            val password = binding.edRegisterPassword.text.toString().trim()
            when {
                email.isEmpty() -> {
                    binding.edLoginEmail.error = resources.getString(R.string.message_validation, "email")
                }
                password.isEmpty() -> {
                    binding.edRegisterPassword.error = resources.getString(R.string.message_validation, "password")
                }
                else -> {
                    if (email.isNotEmpty() && password.isNotEmpty()){
                        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                            if (it.isSuccessful){
                                startActivity(Intent(this, MainActivity::class.java))
                                finish()
//                            val userId = firebaseAuth.currentUser?.uid
//                            Toast.makeText(this, userId.toString(), Toast.LENGTH_SHORT).show()
                            }else{
                                Toast.makeText(this@LoginActivity, "Please Try Again", Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }else{
                        Toast.makeText(this, resources.getString(R.string.login_error), Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            }
        }
    }
}