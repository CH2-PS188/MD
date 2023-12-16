package com.moneo.moneo.ui.transaction

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.moneo.moneo.R
import com.moneo.moneo.ViewModelFactory
import com.moneo.moneo.data.local.rekening.Rekening
import com.moneo.moneo.data.local.transaction.Transaction
import com.moneo.moneo.databinding.ActivityAddUpdateTransactionBinding
import com.moneo.moneo.utils.DatePickerFragment
import com.moneo.moneo.utils.TimePickerFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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

        binding.btnSave.isEnabled = false

        viewModel.rekeningList.observe(this) { listRekening ->
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, listRekening.map { it.name })
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerRekening.adapter = adapter

            val titlebar: String

            if (listRekening.isNotEmpty()) {
                binding.btnSave.isEnabled = true
                binding.warningRekening.isGone = true
            }

            if (isEdit) {
                titlebar = getString(R.string.edit_transaction)
                binding.btnDelete.visibility = View.VISIBLE
                if (transaction != null) {
                    transaction?.let { transaction ->
                        binding.apply {
                            if (transaction.type == "pemasukan") {
                                toggleGroup.check(R.id.btn_income)
                            } else {
                                toggleGroup.check(R.id.btn_expense)
                            }
                            val parts = transaction.date?.split(" ")
                            edtDate.setText(parts?.get(0))
                            edtTime.setText(parts?.get(1))
                            val rekeningIndex = findRekeningIndexInList(listRekening, transaction.rekening)
                            spinnerRekening.setSelection(rekeningIndex)
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
        }

        backToHome()
        showDatePicker()
        showTimePicker()
        saveTransaction()
        deleteTransaction()
    }

    private fun saveTransaction() {
        binding.btnSave.setOnClickListener {
            val date = binding.edtDate.text.toString()
            val time = binding.edtTime.text.toString()
            val rekening = binding.spinnerRekening.selectedItem.toString()
            val total = binding.edtTotal.text.toString()
            val title = binding.edtTitle.text.toString()
            val category = binding.edtCategory.text.toString()
            val description = binding.edtDescription.text.toString()

            if (validateInput(date, time, rekening, total, title, category)) {
                jenisTransaksi = if (binding.toggleGroup.checkedButtonId == R.id.btn_income) {
                    "pemasukan"
                } else {
                    "pengeluaran"
                }
                lifecycleScope.launch(Dispatchers.IO) {
                    transaction?.apply {
                        this.date = "$date $time"
                        this.rekening = rekening
                        this.total = total.toInt()
                        this.title = title
                        this.category = category
                        this.description = description
                        this.type = jenisTransaksi ?: "pengeluaran"
                    }
                    if (isEdit) {
                        viewModel.updateTransaction(transaction as Transaction)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@AddUpdateTransactionActivity, "${transaction?.title} berhasil diubah!", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        viewModel.insertTransaction(transaction as Transaction)
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@AddUpdateTransactionActivity, "${transaction?.title} berhasil dibuat!", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                finish()
            }
        }
    }

    private fun validateInput(date: String, time: String, rekening: String, total: String, title: String, category: String): Boolean {
        return when {
            date.isEmpty() -> {
                binding.edtDate.error = "Field can not be blank"
                false
            }
            time.isEmpty() -> {
                binding.edtTime.error = "Field can not be blank"
                false
            }
            rekening.isEmpty() -> {
                Toast.makeText(this@AddUpdateTransactionActivity, "Rekening kosong", Toast.LENGTH_SHORT).show()
                false
            }
            total.isEmpty() -> {
                binding.edtTotal.error = "Field can not be blank"
                false
            }
            title.isEmpty() -> {
                binding.edtTitle.error = "Field can not be blank"
                false
            }
            category.isEmpty() -> {
                binding.edtCategory.error = "Field can not be blank"
                false
            }
            else -> true
        }
    }

    private fun deleteTransaction() {
        binding.btnDelete.setOnClickListener {
            viewModel.deleteTransaction(transaction as Transaction)
            Toast.makeText(this@AddUpdateTransactionActivity, "${transaction?.title} berhasil dihapus!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun backToHome() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun showDatePicker() {
        binding.btnDate.setOnClickListener {
            val dialogFragment = DatePickerFragment()
            dialogFragment.show(supportFragmentManager, "datePicker")
        }
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        binding.edtDate.setText(dateFormat.format(calendar.time))
        dueDateMillis = calendar.timeInMillis
    }

    private fun showTimePicker() {
        binding.btnTime.setOnClickListener {
            val dialogFragment = TimePickerFragment()
            dialogFragment.show(supportFragmentManager, "timePicker")
        }
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        binding.edtTime.setText(timeFormat.format(calendar.time))
    }

    private fun findRekeningIndexInList(rekeningList: List<Rekening>, rekeningName: String?): Int {
        for ((index, rekening) in rekeningList.withIndex()) {
            if (rekening.name == rekeningName) {
                return index
            }
        }
        return 0
    }


    companion object {
        const val EXTRA_TRANSACTION = "extra_transaction"
    }


}