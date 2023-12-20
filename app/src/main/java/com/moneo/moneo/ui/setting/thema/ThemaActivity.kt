package com.moneo.moneo.ui.setting.thema

import android.content.Context
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.moneo.moneo.R
import com.moneo.moneo.databinding.ActivityThemaBinding
import com.moneo.moneo.datastore.SettingPreferences
import com.moneo.moneo.ui.factory.SettingFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "setting")
@Suppress("DEPRECATION")
class ThemaActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThemaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val pref = SettingPreferences.getInstance(dataStore)
        val themaViewModel = ViewModelProvider(this, SettingFactory(pref))[ThemaViewModel::class.java]



        themaViewModel.getThemeSetting().observe(this){isLightMode ->
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
                themaViewModel.saveThemeSetting(isLightTheme)
            }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }
}