package com.moneo.moneo.ui.transaction

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.moneo.moneo.R
import com.moneo.moneo.data.remote.model.TransactionItem
import com.moneo.moneo.databinding.ActivityAddTransactionBinding
import com.moneo.moneo.ui.factory.TransactionFactory
import com.moneo.moneo.utils.DatePickerFragment
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale



@Suppress("DEPRECATION")
class AddTransactionActivity : AppCompatActivity(), DatePickerFragment.DialogDateListener {

    private var dueDateMillis: Long = System.currentTimeMillis()

    private lateinit var binding: ActivityAddTransactionBinding

    private lateinit var firebaseAuth: FirebaseAuth
    private  lateinit var addTransactionVM: AddTransactionViewModel

    private var jenisTransaksi: String? = null
    private var isEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTransactionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val factory: TransactionFactory = TransactionFactory.getInstance(this)
        addTransactionVM = ViewModelProvider(this, factory)[AddTransactionViewModel::class.java]


        firebaseAuth = FirebaseAuth.getInstance()


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
                    val account = edtRekening.text.toString()
                    val total = edtTotal.text.toString()
                    val title = edtTitle.text.toString()
                    val category = edtCategory.text.toString()
                    val description = edtDescription.text.toString()

                    val idAccount = firebaseAuth.currentUser!!.uid
                    val token = firebaseAuth.currentUser!!.uid
                    val request = TransactionItem(date, account, total, title, category, description)
                    when {
                        date.isEmpty() -> {
                            binding.edtDate.error = "Field can not be blank"
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
                                addTransactionVM.insertTransactionPemasukan(idAccount, token, request)
                                Log.e("Add Pemasukan", "Success: ${request.title} ${request.date} ${request.category} ${request.total} ${request.description}")
                                Toast.makeText(this@AddTransactionActivity, "Pemasukan", Toast.LENGTH_SHORT).show()
                            } else if (jenisTransaksi == "pengeluaran") {
                                addTransactionVM.insertTransactionPengeluaran(idAccount, token, request)
                                Log.e("Add Pengeluaran", "Success: ${request.title} ${request.date} ${request.category} ${request.total} ${request.description}")
                                Toast.makeText(this@AddTransactionActivity, "Pengeluaran", Toast.LENGTH_SHORT).show()
                            }
                            finish()
                        }
                    }
                }
            }
        }

        addTransactionVM.success.observe(this, Observer {
            Log.e("Add Transaction", "Succes: $it")
            finish()
            binding.progressBar.visibility = View.GONE
        })

        addTransactionVM.loading.observe(this, Observer {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        addTransactionVM.error.observe(this, Observer {
            Log.e("Add Transaction", "Error: $it")
            Toast.makeText(this, "Error: $it", Toast.LENGTH_SHORT).show()
            binding.progressBar.visibility = View.GONE
        })

    }


    fun showDatePicker(view: View) {
        val dialogFragment = DatePickerFragment()
        dialogFragment.show(supportFragmentManager, "datePicker")
    }

    override fun onDialogDateSet(tag: String?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, dayOfMonth)
        val dateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        findViewById<TextView>(R.id.edt_date).text = dateFormat.format(calendar.time)

        dueDateMillis = calendar.timeInMillis
    }

    companion object {
        const val EXTRA_TRANSACTION = "extra_transaction"
    }
}
