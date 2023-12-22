package com.moneo.moneo.ui.setting.reminder

import android.Manifest
import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.firebase.auth.FirebaseAuth
import com.moneo.moneo.R
import com.moneo.moneo.ViewModelFactory
import com.moneo.moneo.databinding.ActivityReminderBinding
import com.moneo.moneo.ui.setting.theme.ThemeViewModel
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@Suppress("DEPRECATION")
class ReminderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReminderBinding

    private val viewModel: ReminderViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private lateinit var firebaseAuth: FirebaseAuth

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        firebaseAuth = FirebaseAuth.getInstance()

        viewModel.getNotifikasi().observe(this) {enable ->
            if(enable){
                binding.switchReminder.isChecked = true
                Toast.makeText(this, "Notifikasi Aktif", Toast.LENGTH_SHORT).show()
                notificationWorkManager()
            } else {
                binding.switchReminder.isChecked = false
                Toast.makeText(this, "Notifikasi Tidak Aktif", Toast.LENGTH_SHORT).show()

            }
        }


        binding.switchReminder.setOnCheckedChangeListener{_, isChecked ->
            viewModel.setNotifikasiEnable(isChecked)
        }


        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    @SuppressLint("InvalidPeriodicWorkRequestInterval")
    private fun notificationWorkManager() {
        val constraints = Constraints.Builder()
            .setRequiresCharging(false)
            .setRequiredNetworkType(NetworkType.NOT_REQUIRED)
            .setRequiresCharging(false)
            .setRequiresBatteryNotLow(true)
            .build()

        val myRequest = PeriodicWorkRequest.Builder(
            ReminderWorker::class.java,
            8,
            TimeUnit.HOURS
        ).setConstraints(constraints)
            .build()

        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "my_id",
                ExistingPeriodicWorkPolicy.KEEP,
                myRequest
            )
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                showToast("Notifications permission granted")
            } else {
                showToast("Notifications will not show without permission")
            }
        }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}