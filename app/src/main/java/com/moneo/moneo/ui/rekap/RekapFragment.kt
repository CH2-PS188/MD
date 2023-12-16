package com.moneo.moneo.ui.rekap

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.moneo.moneo.adapter.RekapAdapter
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

        rekapAdapter= RekapAdapter(emptyList())
        binding?.rvRekap?. layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding?.rvRekap?.adapter = rekapAdapter

        val idAccount = firebaseAuth.currentUser!!.uid
        val token = firebaseAuth.currentUser!!.uid

        rekapVM.success.observe(viewLifecycleOwner, Observer{
            rekapAdapter = it?.let { RekapAdapter(listOf(it.summary)) }!!
            binding?.rvRekap?.adapter = rekapAdapter
            binding?.rvRekap?.adapter?.notifyDataSetChanged()
            Log.e("Rekap Fragment", "Succes: ${it.summary}")
            binding?.rvRekap?.visibility = View.VISIBLE
            binding?.progressBar?.visibility = View.GONE
        })

        rekapVM.error.observe(viewLifecycleOwner, Observer {
            binding?.tvNoData?.visibility = View.VISIBLE
            Log.e("Rekap Fragment", "Error: $it")
            Toast.makeText(requireActivity(), "Error: $it", Toast.LENGTH_SHORT).show()
        })

        rekapVM.loading.observe(viewLifecycleOwner, Observer{
            binding?.progressBar?.visibility = View.VISIBLE
        })


        rekapVM.getAllLaporan(idAccount, token)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}
