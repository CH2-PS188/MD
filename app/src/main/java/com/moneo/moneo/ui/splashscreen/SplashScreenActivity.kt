package com.moneo.moneo.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import com.moneo.moneo.R
import com.moneo.moneo.databinding.ActivitySplashScreenBinding
import com.moneo.moneo.ui.onboarding.OnBoardingActivity

@Suppress("DEPRECATION")
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val SPLASH_TIMEOUT = 2000L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.progressBar.visibility = View.VISIBLE

        Handler().postDelayed({
            binding.progressBar.visibility = View.GONE
            val intent = Intent(this, OnBoardingActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_TIMEOUT)

    }
}