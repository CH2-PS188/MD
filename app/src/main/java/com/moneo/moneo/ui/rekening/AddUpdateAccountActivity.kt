package com.moneo.moneo.ui.rekening

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import com.moneo.moneo.MainActivity
import com.moneo.moneo.R
import com.moneo.moneo.ViewModelFactory
import com.moneo.moneo.data.local.account.Account
import com.moneo.moneo.databinding.ActivityAddUpdateAccountBinding

class AddUpdateAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUpdateAccountBinding

    private var account: Account? = null
    private var isEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: AddUpdateAccountViewModel by viewModels {
            factory
        }

        account = intent.getParcelableExtra(EXTRA_ACCOUNT)
        if (account != null) {
            isEdit = true
        } else {
            account = Account()
        }

        val titlebar : String

        if (isEdit) {
            titlebar = getString(R.string.edit_account)
            binding.btnDelete.visibility = View.VISIBLE
            if (account != null) {
                account?.let { account ->
                    binding.edtName.setText(account.name)
                    binding.edtBalance.setText(account.balance.toString())
                }
            }
        } else {
            titlebar = getString(R.string.add_account)
            binding.btnDelete.visibility = View.GONE
        }

        binding.titleAppbarAccount.text = titlebar

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteAccount(account as Account)
            Toast.makeText(this@AddUpdateAccountActivity, "${account?.name} berhasil dihapus!", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.apply {
            btnSubmit.setOnClickListener {
                val name = edtName.text.toString()
                val balance = edtBalance.text.toString()
                when {
                    name.isEmpty() -> {
                        binding.edtName.error = "Field can not be blank"
                    }
                    balance.isEmpty() -> {
                        binding.edtBalance.error = "Field can not be blank"
                    }
                    else -> {
                        account.let { account ->
                            account?.name = name
                            account?.balance = balance.toInt()
                        }
                        if (isEdit) {
                            viewModel.updateAccount(account as Account)
                            Toast.makeText(this@AddUpdateAccountActivity, "${account?.name} berhasil diubah!", Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.createAccount(account as Account)
                            Toast.makeText(this@AddUpdateAccountActivity, "${account?.name} berhasil dibuat!", Toast.LENGTH_SHORT).show()
                        }
                        finish()
                    }
                }
            }
        }
    }

    companion object {
        const val EXTRA_ACCOUNT = "extra_account"
    }
}