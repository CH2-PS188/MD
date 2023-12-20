package com.moneo.moneo.ui.setting.reminder

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.moneo.moneo.databinding.ActivityReminderBinding
import com.moneo.moneo.ui.factory.PrediksiFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@Suppress("DEPRECATION")
class ReminderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReminderBinding
    private lateinit var reminderVM: ReminderViewModel
    private lateinit var firebaseAuth: FirebaseAuth

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReminderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        firebaseAuth = FirebaseAuth.getInstance()


        val reminderManager = ReminderManager(this)

        val reminderFactory: PrediksiFactory  = PrediksiFactory.getInstance(this)
        reminderVM = ViewModelProvider(this, reminderFactory)[ReminderViewModel::class.java]


        lifecycleScope.launch {
            reminderVM.isNotificationEnabled.collect{isEnable ->
                if (isEnable){
                val idAccount = firebaseAuth.currentUser!!.uid
                val token = firebaseAuth.currentUser!!.uid
                 reminderVM.getPrediksi(idAccount, token)
                }
            }
        }

        reminderVM.success.observe(this){prediksiResponse ->
            val notificationData = prediksiResponse.perbandingan
            val idAccount = firebaseAuth.currentUser!!.uid
            reminderManager.scheduleRandomNotifications(idAccount, notificationData)
            Log.e("ReminderActivity", "Success ${prediksiResponse.perbandingan}")
        }

        reminderVM.error.observe(this){errorMessage ->
            Log.e("ReminderActivity", "Error $errorMessage")
            Toast.makeText(this, "Error $errorMessage", Toast.LENGTH_SHORT).show()
        }
        // Update the switch state based on the notification status
        reminderVM.isNotificationEnabled.apply {
            binding.switchReminder.isChecked = true
        }

        // Update the notification status when the switch is toggled
        binding.switchReminder.setOnCheckedChangeListener { _, isChecked ->
            reminderVM.setNotificationEnabled(isChecked)
            Toast.makeText(this, "Notifikasi Berhasil di Aktifkan", Toast.LENGTH_SHORT).show()
        }

//        reminderVM.isNotificationEnabled.apply {
//            binding.switchReminder.isChecked = reminderVM.isNotificationEnabled.value
//        }
//
//
//        binding.switchReminder.setOnClickListener { view: View ->
//            reminderVM.setNotificationEnabled(enabled = true)
//        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }

        if (Build.VERSION.SDK_INT > 32) {
            requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
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