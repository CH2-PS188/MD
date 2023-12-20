package com.moneo.moneo.ui.rekap

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.moneo.moneo.ViewModelFactory
import com.moneo.moneo.data.result.Result
import com.moneo.moneo.databinding.FragmentRekapBinding

class RekapFragment : Fragment() {

    private var _binding: FragmentRekapBinding? = null
    private val binding get() = _binding

    private val viewModel: RekapViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRekapBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRekap()
    }

    private fun setRekap() {

        firebaseAuth = FirebaseAuth.getInstance()

        val idAccount = firebaseAuth.currentUser!!.uid
        val token = firebaseAuth.currentUser!!.uid

        var rekapAdapter = RekapAdapter(emptyList())

        viewModel.getAllLaporan(idAccount, token)
        viewModel.getLaporanResult()?.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        val rekap = result.data
                        rekapAdapter = result.let { RekapAdapter(listOf(rekap)) }
                        binding?.rvRekap?.adapter = rekapAdapter
                    }
                    is Result.Error -> {
                        binding?.progressBar?.visibility = View.GONE
                        binding?.tvNoData?.visibility = View.VISIBLE
                        Toast.makeText(
                            context,
                            "Terjadi kesalahan" + result.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding?.rvRekap?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rekapAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
