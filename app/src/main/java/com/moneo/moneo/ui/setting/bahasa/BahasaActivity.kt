package com.moneo.moneo.ui.setting.bahasa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.moneo.moneo.R
import com.moneo.moneo.databinding.ActivityBahasaBinding

@Suppress("DEPRECATION")
class BahasaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBahasaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBahasaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.radioGroupBahassa.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.rb_languageIndonesia -> Toast.makeText(this, "Bahasa Indonesia", Toast.LENGTH_SHORT).show()
                R.id.rb_languageInggris -> Toast.makeText(this, "Bahasa Inggriss", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}