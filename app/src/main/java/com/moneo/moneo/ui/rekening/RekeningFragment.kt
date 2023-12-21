package com.moneo.moneo.ui.rekening

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.moneo.moneo.ViewModelFactory
import com.moneo.moneo.data.result.Result
import com.moneo.moneo.databinding.FragmentRekeningBinding

class RekeningFragment : Fragment() {

    private var _binding: FragmentRekeningBinding? = null
    private val binding get() = _binding

    private val viewModel: RekeningViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    private lateinit var firebaseAuth: FirebaseAuth

    private val rekeningAdapter = RekeningAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentRekeningBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListRekening()
        setFabClick()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setListRekening() {

        firebaseAuth = FirebaseAuth.getInstance()

        val idAccount = firebaseAuth.currentUser!!.uid
        val token = firebaseAuth.currentUser!!.uid

        binding?.rvRekening?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rekeningAdapter
        }

        viewModel.getAllRekening(idAccount, token)
        viewModel.getRekeningResult()?.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        val listRekening = result.data
                        rekeningAdapter.submitList(listRekening)
                        rekeningAdapter.notifyDataSetChanged()
                        binding?.rvRekening?.adapter = rekeningAdapter
                        Log.d("REKENING-DATA", "$listRekening")
                    }
                    is Result.Error -> {
                        binding?.progressBar?.visibility = View.GONE
                        Toast.makeText(
                            context,
                            "Terjadi kesalahan: ${result.error}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setFabClick() {
        binding?.btnFab?.setOnClickListener {
            val intent = Intent(activity, AddUpdateRekeningActivity::class.java)
            startActivity(intent)
        }
    }
}