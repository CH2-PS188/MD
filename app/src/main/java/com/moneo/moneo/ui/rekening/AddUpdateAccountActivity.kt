package com.moneo.moneo.ui.rekening

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.moneo.moneo.databinding.ActivityAddUpdateAccountBinding

class AddUpdateAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUpdateAccountBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
    }
}