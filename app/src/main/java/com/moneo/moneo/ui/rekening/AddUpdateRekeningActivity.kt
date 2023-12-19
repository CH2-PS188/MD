package com.moneo.moneo.ui.rekening

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.moneo.moneo.R
import com.moneo.moneo.ViewModelFactory
import com.moneo.moneo.data.local.rekening.Rekening
import com.moneo.moneo.data.remote.response.toRekeningsItem
import com.moneo.moneo.databinding.ActivityAddUpdateRekeningBinding

class AddUpdateRekeningActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUpdateRekeningBinding

    private var rekening: Rekening? = null
    private var isEdit = false

    private val viewModel: AddUpdateRekeningViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateRekeningBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        rekening = intent.getParcelableExtra(EXTRA_ACCOUNT)
        if (rekening != null) {
            isEdit = true
        } else {
            rekening = Rekening()
        }

        val titleBar : String

        if (isEdit) {
            titleBar = getString(R.string.edit_account)
            binding.btnDelete.visibility = View.VISIBLE
            if (rekening != null) {
                rekening?.let { rekening ->
                    binding.edtName.setText(rekening.name)
                    binding.edtBalance.setText(rekening.balance.toString())
                }
            }
        } else {
            titleBar = getString(R.string.add_account)
            binding.btnDelete.visibility = View.GONE
        }

        binding.titleAppbarRekening.text = titleBar

        saveRekening()
        backToHome()
        deleteRekening()
    }

    private fun backToHome() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun saveRekening() {
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
                        val idAccount = firebaseAuth.currentUser!!.uid
                        val token = firebaseAuth.currentUser!!.uid
                        rekening.let { rekening ->
                            rekening?.idAccount = idAccount
                            rekening?.name = name
                            rekening?.balance = balance.toInt()
                        }
                        val rekeningsItem = rekening?.toRekeningsItem()
                        if (rekeningsItem != null) {
                            if (isEdit) {
                                viewModel.updateRekening(idAccount, token, rekeningsItem.id, rekeningsItem)
                                Toast.makeText(this@AddUpdateRekeningActivity, "${rekening?.name} berhasil diubah!", Toast.LENGTH_SHORT).show()
                            } else {
                                viewModel.createRekening(idAccount, token, rekeningsItem)
                                Toast.makeText(this@AddUpdateRekeningActivity, "${rekening?.name} berhasil dibuat!", Toast.LENGTH_SHORT).show()
                            }
                        }
                        finish()
                    }
                }
            }
        }
    }

    private fun deleteRekening() {
        binding.btnDelete.setOnClickListener {
            val idAccount = firebaseAuth.currentUser!!.uid
            val token = firebaseAuth.currentUser!!.uid
            rekening?.let {
                viewModel.deleteRekening(idAccount, token, it.rekeningId)
                Log.d("activity", "${it.rekeningId}")
            }
            Toast.makeText(this@AddUpdateRekeningActivity, "${rekening?.name} berhasil dihapus!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    companion object {
        const val EXTRA_ACCOUNT = "extra_account"
    }
}