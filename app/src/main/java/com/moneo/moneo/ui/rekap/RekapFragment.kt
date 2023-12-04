package com.moneo.moneo.ui.rekap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.moneo.moneo.R
import com.moneo.moneo.databinding.FragmentRekapBinding

class RekapFragment : Fragment() {

    private var _binding: FragmentRekapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRekapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set text to the TextView
        binding.textRekap.text = getString(R.string.navigation_transaksi)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}