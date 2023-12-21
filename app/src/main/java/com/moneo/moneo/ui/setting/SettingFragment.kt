package com.moneo.moneo.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.moneo.moneo.databinding.FragmentSettingBinding
import android.app.AlertDialog
import android.content.Intent
import com.moneo.moneo.ui.setting.bahasa.BahasaActivity
import com.moneo.moneo.ui.setting.reminder.ReminderActivity
import com.moneo.moneo.ui.setting.theme.ThemeActivity

class SettingFragment : Fragment() {
    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding

    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        binding?.cardKeluar?.setOnClickListener {
            binding?.cardKeluar?.setOnClickListener {
                AlertDialog.Builder(requireActivity()).apply {
                    setTitle("Peringatan!")
                    setMessage("Apakah anda yaki ingin keluar?")
                    setPositiveButton("Ya") { _, _ ->
                        firebaseAuth.signOut()
                        activity?.finish()
                    }
                    setNegativeButton("Tidak") { dialog, _ ->
                        dialog.dismiss()
                    }
                    create()
                    show()
                }
            }
        }

        binding?.cardNotifications?.setOnClickListener {
            val intent = Intent(context, ReminderActivity::class.java)
            startActivity(intent)
        }

        binding?.cardThema?.setOnClickListener {
            val intent = Intent(context, ThemeActivity::class.java)
            startActivity(intent)
        }

        binding?.cardBahasa?.setOnClickListener {
            val intent = Intent(context, BahasaActivity::class.java)
            startActivity(intent)
        }
    }
}