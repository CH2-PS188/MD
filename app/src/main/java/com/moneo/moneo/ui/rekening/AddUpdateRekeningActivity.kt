package com.moneo.moneo.ui.rekening

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.moneo.moneo.R
import com.moneo.moneo.ViewModelFactory
import com.moneo.moneo.data.local.rekening.Rekening
import com.moneo.moneo.databinding.ActivityAddUpdateRekeningBinding

class AddUpdateRekeningActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUpdateRekeningBinding

    private var rekening: Rekening? = null
    private var isEdit = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateRekeningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val viewModel: AddUpdateRekeningViewModel by viewModels {
            factory
        }

        rekening = intent.getParcelableExtra(EXTRA_ACCOUNT)
        if (rekening != null) {
            isEdit = true
        } else {
            rekening = Rekening()
        }

        val titlebar : String

        if (isEdit) {
            titlebar = getString(R.string.edit_account)
            binding.btnDelete.visibility = View.VISIBLE
            if (rekening != null) {
                rekening?.let { account ->
                    binding.edtName.setText(account.name)
                    binding.edtBalance.setText(account.balance.toString())
                }
            }
        } else {
            titlebar = getString(R.string.add_account)
            binding.btnDelete.visibility = View.GONE
        }

        binding.titleAppbarRekening.text = titlebar

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnDelete.setOnClickListener {
            viewModel.deleteRekening(rekening as Rekening)
            Toast.makeText(this@AddUpdateRekeningActivity, "${rekening?.name} berhasil dihapus!", Toast.LENGTH_SHORT).show()
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
                        rekening.let { account ->
                            account?.name = name
                            account?.balance = balance.toInt()
                        }
                        if (isEdit) {
                            viewModel.updateRekening(rekening as Rekening)
                            Toast.makeText(this@AddUpdateRekeningActivity, "${rekening?.name} berhasil diubah!", Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.createRekening(rekening as Rekening)
                            Toast.makeText(this@AddUpdateRekeningActivity, "${rekening?.name} berhasil dibuat!", Toast.LENGTH_SHORT).show()
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