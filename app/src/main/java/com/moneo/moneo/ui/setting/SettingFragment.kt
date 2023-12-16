package com.moneo.moneo.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.moneo.moneo.databinding.FragmentSettingBinding
import android.app.AlertDialog


class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding

//    private lateinit var customAlertDialogBinding: CustomAlertDialogBinding
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
    }
}


//    private fun showCustomAlertDialog() {
//        val dialogBuilder = AlertDialog.Builder(requireContext())
//        val inflater = LayoutInflater.from(requireContext())
//        val dialogView = inflater.inflate(R.layout.custom_alert_dialog, null)
//        dialogBuilder.setView(dialogView)
//
//        val alertDialog = dialogBuilder.create()
//
//        customAlertDialogBinding.tvPeringatan.text = getString(R.string.peringatan)
//        customAlertDialogBinding.tvSubPeringatan.text = getString(R.string.sub_peringatan)
//
//        customAlertDialogBinding.btnYes.setOnClickListener {
//            firebaseAuth.signOut()
//            activity?.finish()
//        }
//
//        customAlertDialogBinding.btnNo.setOnClickListener {
//            alertDialog.dismiss()
//        }
//        alertDialog.show()
//    }
//
//}

//binding?.cardKeluar?.setOnClickListener {
//    AlertDialog.Builder(requireActivity()).apply {
//        setTitle("Peringatan!")
//        setMessage("Apakah anda yaki ingin keluar?")
//        setPositiveButton("Ya") { _, _ ->
//            firebaseAuth.signOut()
//            activity?.finish()
//        }
//        setNegativeButton("Tidak"){ dialog, _ ->
//            dialog.dismiss()
//        }
//        create()
//        show()
//    }
//}