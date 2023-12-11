package com.moneo.moneo.ui.rekening

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.moneo.moneo.MainActivity
import com.moneo.moneo.R
import com.moneo.moneo.ViewModelFactory
import com.moneo.moneo.data.local.account.Account
import com.moneo.moneo.databinding.ActivityAddUpdateAccountBinding
import com.moneo.moneo.ui.transaction.AddTransactionViewModel

class AddUpdateAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUpdateAccountBinding

    private var account: Account? = null
    private var isEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (account != null) {
            isEdit = true
        } else {
            account = Account()
        }

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: AddUpdateAccountViewModel by viewModels {
            factory
        }

        binding.btnBack.setOnClickListener {
            intent = Intent(this@AddUpdateAccountActivity, MainActivity::class.java)
            startActivity(intent)
        }

        binding.apply {
            btnSubmit.setOnClickListener {
                val name = edtName.text.toString()
                val balance = edtBalance.text.toString()

                account.let { account ->
                    account?.name = name
                    account?.balance = balance.toInt()
                }

                viewModel.createAccount(account as Account)

                Toast.makeText(this@AddUpdateAccountActivity, "${account?.name} berhasil dibuat!", Toast.LENGTH_SHORT).show()
                intent = Intent(this@AddUpdateAccountActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }
}