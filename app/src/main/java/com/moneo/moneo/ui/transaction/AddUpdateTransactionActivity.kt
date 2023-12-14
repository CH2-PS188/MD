package com.moneo.moneo.ui.transaction

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.moneo.moneo.R
import com.moneo.moneo.ViewModelFactory
import com.moneo.moneo.data.local.transaction.Transaction
import com.moneo.moneo.databinding.ActivityAddUpdateTransactionBinding
import com.moneo.moneo.utils.DatePickerFragment
import com.moneo.moneo.utils.TimePickerFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddUpdateTransactionActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener, TimePickerFragment.DialogTimeListener {

    private var dueDateMillis: Long = System.currentTimeMillis()

    private lateinit var binding: ActivityAddUpdateTransactionBinding

    private val viewModel: AddUpdateTransactionViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private var transaction: Transaction? = null
    private var isEdit = false

    private var jenisTransaksi: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                        val parts = transaction.date?.split(" ")
                        edtDate.setText(parts?.get(0))
                        edtTime.setText(parts?.get(1))
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
            Toast.makeText(this@AddUpdateTransactionActivity, "${transaction?.title} berhasil dihapus!", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.btnDate.setOnClickListener {
            showDatePicker()
        }

        binding.btnTime.setOnClickListener {
            showTimePicker()
        }

        binding.apply {
            toggleGroup.addOnButtonCheckedListener { group, _, _ ->
                jenisTransaksi = if (group.checkedButtonId == R.id.btn_income) {
                    Toast.makeText(this@AddUpdateTransactionActivity, "${btnIncome.text}", Toast.LENGTH_SHORT).show()
                    "pemasukan"
                } else {
                    Toast.makeText(this@AddUpdateTransactionActivity, "${btnExpense.text}", Toast.LENGTH_SHORT).show()
                    "pengeluaran"
                }
            }

            btnSave.setOnClickListener {
                val date = edtDate.text.toString()
                val time = edtTime.text.toString()
                val total = edtTotal.text.toString()
                val title = edtTitle.text.toString()
                val category = edtCategory.text.toString()
                val description = edtDescription.text.toString()
                when {
                    date.isEmpty() -> {
                        binding.edtDate.error = "Field can not be blank"
                    }
                    time.isEmpty() -> {
                        binding.edtTime.error = "Field can not be blank"
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
                            transaction?.date = "$date $time"
                            transaction?.total = total.toInt()
                            transaction?.title = title
                            transaction?.category = category
                            transaction?.description = description
                            transaction?.type = jenisTransaksi ?: "pengeluaran"
                        }

                        if (isEdit) {
                            viewModel.updateTransaction(transaction as Transaction)
                            Toast.makeText(this@AddUpdateTransactionActivity, "${transaction?.title} berhasil diubah!", Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.insertTransaction(transaction as Transaction)
                            Toast.makeText(this@AddUpdateTransactionActivity, "${transaction?.title} berhasil dibuat!", Toast.LENGTH_SHORT).show()
                        }

                        finish()
                    }
                }
            }
        }
    }

    private fun showDatePicker() {
        val dialogFragment = DatePickerFragment()
        dialogFragment.show(supportFragmentManager, "datePicker")
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        binding.edtDate.setText(dateFormat.format(calendar.time))
        dueDateMillis = calendar.timeInMillis
    }

    private fun showTimePicker() {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, "timePicker")
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        binding.edtTime.setText(timeFormat.format(calendar.time))
    }

    companion object {
        const val EXTRA_TRANSACTION = "extra_transaction"
    }


}