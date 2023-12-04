package com.moneo.moneo.transaction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.moneo.moneo.R
import com.moneo.moneo.databinding.ActivityTransactionBinding

class TransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTransactionBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnFab.setOnClickListener {
            intent = Intent(this@TransactionActivity, AddTransactionActivity::class.java)
            startActivity(intent)
        }
    }
}