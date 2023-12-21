package com.moneo.moneo.ui.setting.theme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.moneo.moneo.R
import com.moneo.moneo.ViewModelFactory
import com.moneo.moneo.databinding.ActivityThemeBinding
import com.moneo.moneo.datastore.SettingPreferences
import com.moneo.moneo.ui.transaction.AddUpdateTransactionViewModel

class ThemeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityThemeBinding

    private val viewModel: ThemeViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getThemeSetting().observe(this){ isLightMode ->
            Log.d("ThemaActivity", "Theme setting changed: $isLightMode")
            binding.rbLighTheme.isChecked = isLightMode
            binding.rbDarkTheme.isChecked = !isLightMode
            if (isLightMode){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Toast.makeText(this, "Mode Terang", Toast.LENGTH_SHORT).show()
            } else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Toast.makeText(this, "Mode Gelap", Toast.LENGTH_SHORT).show()
            }
        }

        binding.radioGroupThema.setOnCheckedChangeListener { _, checkedId ->
            val isLightTheme = binding.rbLighTheme.isChecked
            viewModel.saveThemeSetting(isLightTheme)
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}