package com.moneo.moneo.ui.rekening

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.moneo.moneo.R
import com.moneo.moneo.data.remote.model.RekeningsItem
import com.moneo.moneo.databinding.ActivityAddUpdateAccountBinding
import com.moneo.moneo.ui.factory.RekeningFactory

@Suppress("DEPRECATION")
class AddUpdateAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUpdateAccountBinding

    private lateinit var addRekeningVM: AddUpdateAccountViewModel
    private lateinit var firebaseAuth: FirebaseAuth

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUpdateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val factory: RekeningFactory = RekeningFactory.getInstance(this)
        addRekeningVM = ViewModelProvider(this, factory)[AddUpdateAccountViewModel::class.java]

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnSubmit.setOnClickListener {
        val name = binding.edtName.text.toString()
        val balance = binding.edtBalance.text.toString()
        val idAccount = firebaseAuth.currentUser!!.uid
        val token = firebaseAuth.currentUser!!.uid
        val rekening = RekeningsItem(judul = name, saldo = balance)
            when {
                name.isEmpty() -> {
                    binding.edtName.error = resources.getString(R.string.message_validation, "Name Rekening")
                }
                balance.isEmpty() -> {
                    binding.edtName.error = resources.getString(R.string.message_validation, "Saldo")
                }
                else -> {
                    if (name.isNotEmpty() && balance.isNotEmpty()){
                        addRekeningVM.insertRekening(idAccount, token, rekening)
                        Log.e("Add Rekening", "Succes: ${rekening.judul} ${rekening.saldo}")
                        finish()
                    } else{
                        Toast.makeText(this, resources.getString(R.string.rekening_error), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        addRekeningVM.success.observe(this, Observer {
            Log.e("Add Rekening", "Succes: ${it.rekenings}")
            binding.progressBar.visibility = View.GONE
        })

        addRekeningVM.loading.observe(this, Observer{
            binding.progressBar.visibility = View.VISIBLE
        })

        addRekeningVM.error.observe(this, Observer {
            Log.e("Add Rekening", "Error: $it")
            Toast.makeText(this, "Error: $it", Toast.LENGTH_SHORT).show()
        })

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}