package com.moneo.moneo.ui.transaction

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.moneo.moneo.R
import com.moneo.moneo.data.remote.model.TransactionItem
import com.moneo.moneo.data.result.Result
import com.moneo.moneo.databinding.ActivityAddTransactionBinding
import com.moneo.moneo.ui.factory.AddTransactionFactory
import com.moneo.moneo.utils.DatePickerFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Suppress("DEPRECATION")
class AddTransactionActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener {

    private var dueDateMillis: Long = System.currentTimeMillis()

    private lateinit var binding: ActivityAddTransactionBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private  lateinit var viewModel: AddTransactionViewModel


    private var isEdit = false

    private var jenisTransaksi: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        viewModel = ViewModelProvider (this)[AddTransactionViewModel::class.java]


        firebaseAuth = FirebaseAuth.getInstance()
        val idAccount = firebaseAuth.currentUser!!.uid
        val token = firebaseAuth.currentUser!!.uid


        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        binding.apply {
            toggleGroup.addOnButtonCheckedListener { group, _, _ ->
                jenisTransaksi = when (group.checkedButtonId) {
                    R.id.btn_income -> {
                        Toast.makeText(
                            this@AddTransactionActivity,
                            "${btnIncome.text}",
                            Toast.LENGTH_SHORT
                        ).show()
                        "pemasukan"
                    }

                    R.id.btn_expense -> {
                        Toast.makeText(
                            this@AddTransactionActivity,
                            "${btnExpense.text}",
                            Toast.LENGTH_SHORT
                        ).show()
                        "pengeluaran"
                    }

                    else -> null
                }
            }

            binding.apply {
                toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
                    if (isChecked) {
                        jenisTransaksi = when (checkedId) {
                            R.id.btn_income -> {
                                Toast.makeText(
                                    this@AddTransactionActivity,
                                    "Income selected",
                                    Toast.LENGTH_SHORT
                                ).show()
                                "pemasukan"
                            }

                            R.id.btn_expense -> {
                                Toast.makeText(
                                    this@AddTransactionActivity,
                                    "Expense selected",
                                    Toast.LENGTH_SHORT
                                ).show()
                                "pengeluaran"
                            }

                            else -> null
                        }
                    }
                }

                binding.btnSave.setOnClickListener {
                    val date = edtDate.text.toString()
                    val rekening = edtRekening.text.toString()
                    val total = edtTotal.text.toString()
                    val title = edtTitle.text.toString()
                    val category = edtCategory.text.toString()
                    val description = edtDescription.text.toString()
                    val request = TransactionItem(date, rekening, total, title, category, description)
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
                            if (jenisTransaksi == "pemasukan") {
                                Toast.makeText(this@AddTransactionActivity, "Pemasukan", Toast.LENGTH_SHORT).show()
                            } else if (jenisTransaksi == "pengeluaran") {
                                Toast.makeText(this@AddTransactionActivity, "Pengeluaran", Toast.LENGTH_SHORT).show()
                            }
                            finish()
                        }
                    }
                }
            }
        }
    }

//        binding.apply {
//            toggleGroup.addOnButtonCheckedListener { group, _, _ ->
//                jenisTransaksi = when (group.checkedButtonId) {
//                    R.id.btn_income -> {
//                        Toast.makeText(
//                            this@AddTransactionActivity,
//                            "${btnIncome.text}",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        "pemasukan"
//                    }
//
//                    R.id.btn_expense -> {
//                        Toast.makeText(
//                            this@AddTransactionActivity,
//                            "${btnExpense.text}",
//                            Toast.LENGTH_SHORT
//                        ).show()
//                        "pengeluaran"
//                    }
//
//                    else -> null
//                }
//            }
//
//            binding.apply {
//                toggleGroup.addOnButtonCheckedListener { _, checkedId, isChecked ->
//                    if (isChecked) {
//                        jenisTransaksi = when (checkedId) {
//                            R.id.btn_income -> {
//                                Toast.makeText(
//                                    this@AddTransactionActivity,
//                                    "Income selected",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                                "pemasukan"
//                            }
//
//                            R.id.btn_expense -> {
//                                Toast.makeText(
//                                    this@AddTransactionActivity,
//                                    "Expense selected",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                                "pengeluaran"
//                            }
//
//                            else -> null
//                        }
//                    }
//                }
//
//                binding.btnSave.setOnClickListener {
//                    val date = edtDate.text.toString()
//                    val rekening = edtRekening.text.toString()
//                    val total = edtTotal.text.toString()
//                    val title = edtTitle.text.toString()
//                    val category = edtCategory.text.toString()
//                    val description = edtDescription.text.toString()
//                    val request = TransactionItem(date, rekening, total, title, category, description)
//
//
//                    when {
//                        date.isEmpty() -> {
//                            binding.edtDate.error = "Field can not be blank"
//                        }
//
//                        rekening.isEmpty() -> {
//                            binding.edtRekening.error = "Field can not be blank"
//                        }
//
//                        total.isEmpty() -> {
//                            binding.edtTotal.error = "Field can not be blank"
//                        }
//
//                        title.isEmpty() -> {
//                            binding.edtTitle.error = "Field can not be blank"
//                        }
//
//                        category.isEmpty() -> {
//                            binding.edtCategory.error = "Field can not be blank"
//                        }
//
//                        description.isEmpty() -> {
//                            binding.edtDescription.error = "Field can not be blank"
//                        }
//
//                        else -> {
//                            if (jenisTransaksi == "pemasukan") {
//                                viewModel.insertTransactionPemasukan(idAccount, token, request)
//                                    .observe(this@AddTransactionActivity) { result ->
//                                        if (result != null) {
//                                            when (result) {
//                                                is Result.Loading -> {
//                                                    binding.progressBar.visibility = View.VISIBLE
//                                                }
//
//                                                is Result.Success -> {
//                                                    binding.progressBar.visibility = View.GONE
////                                                    result.data
//                                                    Toast.makeText(
//                                                        this@AddTransactionActivity,
//                                                        "Success",
//                                                        Toast.LENGTH_SHORT
//                                                    )
//                                                        .show()
//                                                }
//
//                                                is Result.Error -> {
//                                                    binding.progressBar.visibility = View.GONE
//                                                    Toast.makeText(
//                                                        this@AddTransactionActivity,
//                                                        "Error: ${result.error}",
//                                                        Toast.LENGTH_SHORT
//                                                    ).show()
//                                                }
//
//                                            }
//                                        }
//                                    }
//                            } else if (jenisTransaksi == "pengeluaran") {
//                                viewModel.insertTransactionPengeluaran(idAccount, token, request)
//                                    .observe(this@AddTransactionActivity) { result ->
//                                        if (result != null) {
//                                            when (result) {
//                                                is Result.Loading -> {
//                                                    binding.progressBar.visibility = View.VISIBLE
//                                                }
//
//                                                is Result.Success -> {
//                                                    binding.progressBar.visibility = View.GONE
////                                                    result.data.listTransaction
//                                                    Toast.makeText(
//                                                        this@AddTransactionActivity,
//                                                        "Success",
//                                                        Toast.LENGTH_SHORT
//                                                    )
//                                                        .show()
//                                                }
//
//                                                is Result.Error -> {
//                                                    binding.progressBar.visibility = View.GONE
//                                                    Toast.makeText(
//                                                        this@AddTransactionActivity,
//                                                        "Error: ${result.error}",
//                                                        Toast.LENGTH_SHORT
//                                                    ).show()
//                                                }
//
//                                            }
//                                        }
//
//                                        Toast.makeText(this@AddTransactionActivity, "Transaksi Berhasil di buat", Toast.LENGTH_SHORT).show()
//                                }
//                            }
//                            finish()
//                        }
//                    }
//                }
//            }
//        }
//    }


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