package com.moneo.moneo.ui.transaction

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.moneo.moneo.MainActivity
import com.moneo.moneo.ViewModelFactory
import com.moneo.moneo.data.local.Transaction
import com.moneo.moneo.databinding.ActivityAddTransactionBinding

class AddTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTransactionBinding
    private var transaction: Transaction? = null
    private var isEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (transaction != null) {
            isEdit = true
        } else {
            transaction = Transaction()
        }

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: AddTransactionViewModel by viewModels {
            factory
        }

        binding.btnBack.setOnClickListener {
            intent = Intent(this@AddTransactionActivity, MainActivity::class.java)
            startActivity(intent)
        }

        binding.apply {
            btnSave.setOnClickListener {
                val date = edtDate.text.toString()
                val rekening = edtRekening.text.toString()
                val total = edtTotal.text.toString()
                val title = edtTitle.text.toString()
                val category = edtCategory.text.toString()
                val description = edtDescription.text.toString()
                transaction.let { transaction ->
                    transaction?.date = date
                    transaction?.account = rekening
                    transaction?.total = total.toInt()
                    transaction?.title = title
                    transaction?.category = category
                    transaction?.description = description
                }
                viewModel.insertTransaction(transaction as Transaction)
                Toast.makeText(this@AddTransactionActivity, "Transaksi berhasil ditambahkan", Toast.LENGTH_SHORT).show()
                intent = Intent(this@AddTransactionActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}