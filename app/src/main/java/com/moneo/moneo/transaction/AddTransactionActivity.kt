package com.moneo.moneo.transaction

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.moneo.moneo.R
import com.moneo.moneo.databinding.ActivityAddTransactionBinding
import com.moneo.moneo.databinding.ActivityTransactionBinding

class AddTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBack.setOnClickListener {
            intent = Intent(this@AddTransactionActivity, TransactionActivity::class.java)
            startActivity(intent)
        }

        binding.btnSave.setOnClickListener {
            intent = Intent(this@AddTransactionActivity, TransactionActivity::class.java)
            startActivity(intent)
        }
    }
}