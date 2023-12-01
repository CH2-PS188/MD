package com.moneo.moneo.ui.registrasi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.moneo.moneo.R
import com.moneo.moneo.databinding.ActivityLoginBinding

class RegistrasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrasi)
    }
}