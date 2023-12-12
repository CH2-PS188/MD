package com.moneo.moneo.ui.rekap

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.moneo.moneo.adapter.RekapAdapter
import com.moneo.moneo.data.result.Result
import com.moneo.moneo.databinding.FragmentRekapBinding
import com.moneo.moneo.ui.factory.RekapFactory

class RekapFragment : Fragment() {

    private var _binding: FragmentRekapBinding? = null
    private val binding get() = _binding
    private lateinit var rekapVM: RekapViewModel
    private lateinit var rekapAdapter: RekapAdapter
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

        val rekapFactory: RekapFactory = RekapFactory.getInstance(requireActivity())
        rekapVM = ViewModelProvider(this, rekapFactory)[RekapViewModel::class.java]

        firebaseAuth = FirebaseAuth.getInstance()
        rekapAdapter= RekapAdapter()

        binding?.rvRekap?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rekapAdapter
            setHasFixedSize(true)
        }

        val userId = firebaseAuth.currentUser?.uid
        rekapVM.getAllLaporan(userId.toString()).observe(viewLifecycleOwner){ result ->
            if (result != null){
                when(result) {
                    is Result.Loading -> {
                        binding?.progressBar?.visibility = View.VISIBLE
                        binding?.tvNoData?.visibility = View.GONE
                    }

                    is Result.Success -> {
                        binding?.progressBar?.visibility = View.GONE
                        binding?.tvNoData?.visibility = View.GONE
                        rekapAdapter.submitList(result.data.listRekap)
                        rekapAdapter.notifyDataSetChanged()
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    }

                    is Result.Error -> {
                        binding?.progressBar?.visibility = View.GONE
                        binding?.tvNoData?.visibility = View.VISIBLE
                        Toast.makeText(context, "Error: ${result.error}", Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}

