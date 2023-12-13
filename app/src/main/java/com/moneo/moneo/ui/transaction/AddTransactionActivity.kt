package com.moneo.moneo.ui.transaction

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.moneo.moneo.MainActivity
import com.moneo.moneo.R
import com.moneo.moneo.ViewModelFactory
import com.moneo.moneo.data.local.transaction.Transaction
import com.moneo.moneo.databinding.ActivityAddTransactionBinding
import com.moneo.moneo.utils.DatePickerFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddTransactionActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener {

    private var dueDateMillis: Long = System.currentTimeMillis()

    private lateinit var binding: ActivityAddTransactionBinding

    private var transaction: Transaction? = null
    private var isEdit = false

    private var jenisTransaksi: String? = null

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

        val jenis = transaction?.type

        val titlebar: String

        if (isEdit) {
            titlebar = getString(R.string.edit_transaction)
            binding.btnDelete.visibility = View.VISIBLE
            if (transaction != null) {
                transaction?.let { transaction ->
                    binding.apply {
                        if (jenis == "pemasukan") {
                            toggleGroup.check(R.id.btn_income)
                        } else {
                            toggleGroup.check(R.id.btn_expense)
                        }
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
            toggleGroup.addOnButtonCheckedListener { group, _, _ ->
                jenisTransaksi = if (group.checkedButtonId == R.id.btn_income) {
                    Toast.makeText(this@AddTransactionActivity, "${btnIncome.text}", Toast.LENGTH_SHORT).show()
                    "pemasukan"
                } else {
                    Toast.makeText(this@AddTransactionActivity, "${btnExpense.text}", Toast.LENGTH_SHORT).show()
                    "pengeluaran"
                }
            }

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
                            transaction?.type = jenisTransaksi
                        }
                        if (isEdit) {
                            viewModel.updateTransaction(transaction as Transaction)
                            Toast.makeText(this@AddTransactionActivity, "${transaction?.title} berhasil diubah!", Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.insertTransaction(transaction as Transaction)
                            Toast.makeText(this@AddTransactionActivity, "${transaction?.title} berhasil dibuat!", Toast.LENGTH_SHORT).show()
                        }
                        finish()
                    }
                }
            }
        }
    }

    fun showDatePicker(view: View) {
        val dialogFragment = DatePickerFragment()
        dialogFragment.show(supportFragmentManager, "datePicker")
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        findViewById<TextView>(R.id.edt_date).text = dateFormat.format(calendar.time)

        dueDateMillis = calendar.timeInMillis
    }

    companion object {
        const val EXTRA_TRANSACTION = "extra_transaction"
    }
}