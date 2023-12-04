package com.moneo.moneo.ui.tabungan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.moneo.moneo.R
import com.moneo.moneo.databinding.FragmentTabunganBinding

class TabunganFragment : Fragment() {

    private var _binding: FragmentTabunganBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabunganBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set text to the TextView
        binding.textTabungan.text = getString(R.string.navigation_transaksi)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}