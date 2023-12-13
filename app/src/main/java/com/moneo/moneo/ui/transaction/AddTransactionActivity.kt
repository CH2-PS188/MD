package com.moneo.moneo.ui.transaction

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.moneo.moneo.MainActivity
import com.moneo.moneo.R
import com.moneo.moneo.ViewModelFactory
import com.moneo.moneo.data.local.transaction.Transaction
import com.moneo.moneo.databinding.ActivityAddTransactionBinding

class AddTransactionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTransactionBinding

    private var transaction: Transaction? = null
    private var isEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: AddTransactionViewModel by viewModels {
            factory
        }


        transaction = intent.getParcelableExtra(EXTRA_TRANSACTION)
        if (transaction != null) {
            isEdit = true
        } else {
            transaction = Transaction()
        }

        val titlebar: String

        if (isEdit) {
            titlebar = getString(R.string.edit_transaction)
            binding.btnDelete.visibility = View.VISIBLE
            if (transaction != null) {
                transaction?.let { transaction ->
                    binding.apply {
                        edtDate.setText(transaction.date.toString())
                        edtRekening.setText(transaction.account)
                        edtTotal.setText(transaction.total.toString())
                        edtTitle.setText(transaction.title)
                        edtCategory.setText(transaction.category)
                        edtDescription.setText(transaction.description)
                    }
                }
            }
        } else {
            titlebar = getString(R.string.tambah_transaction)
            binding.btnDelete.visibility = View.GONE
        }

        binding.titleAppbarTransaction.text = titlebar

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteTransaction(transaction as Transaction)
            Toast.makeText(this@AddTransactionActivity, "${transaction?.title} berhasil dihapus!", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.apply {
            btnSave.setOnClickListener {
                val date = edtDate.text.toString()
                val rekening = edtRekening.text.toString()
                val total = edtTotal.text.toString()
                val title = edtTitle.text.toString()
                val category = edtCategory.text.toString()
                val description = edtDescription.text.toString()
                when {
                    date.isEmpty() -> {
                        binding.edtDate.error = "Field can not be blank"
                    }
                    rekening.isEmpty() -> {
                        binding.edtRekening.error = "Field can not be blank"
                    }
                    total.isEmpty() -> {
                        binding.edtTotal.error = "Field can not be blank"
                    }
                    title.isEmpty() -> {
                        binding.edtTitle.error = "Field can not be blank"
                    }
                    category.isEmpty() -> {
                        binding.edtCategory.error = "Field can not be blank"
                    }
                    description.isEmpty() -> {
                        binding.edtDescription.error = "Field can not be blank"
                    }
                    else -> {
                        transaction.let { transaction ->
                            transaction?.date = date
                            transaction?.account = rekening
                            transaction?.total = total.toInt()
                            transaction?.title = title
                            transaction?.category = category
                            transaction?.description = description
                        }
                        if (isEdit) {
                            viewModel.updateTransaction(transaction as Transaction)
                        } else {
                            viewModel.insertTransaction(transaction as Transaction)
                        }
                        finish()
                    }
                }
            }
        }
    }

    companion object {
        const val EXTRA_TRANSACTION = "extra_transaction"
    }
}