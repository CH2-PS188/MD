package com.moneo.moneo.ui.transaction

import android.annotation.SuppressLint
import android.content.Intent
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
import com.moneo.moneo.adapter.TransactionAdapter
import com.moneo.moneo.databinding.FragmentTransactionBinding
import com.moneo.moneo.ui.factory.TransactionFactory

class TransactionFragment : Fragment() {

    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var transactionAdapter: TransactionAdapter
    private lateinit var transaksiVM: TransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: TransactionFactory = TransactionFactory.getInstance(requireActivity())
        transaksiVM = ViewModelProvider(this, factory)[TransactionViewModel::class.java]

        firebaseAuth = FirebaseAuth.getInstance()

        transactionAdapter = TransactionAdapter(emptyList())
        binding?.rvTransaction?.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding?.rvTransaction?.adapter = transactionAdapter


        val idAccount = firebaseAuth.currentUser!!.uid
        val token = firebaseAuth.currentUser!!.uid

        transaksiVM.success.observe(viewLifecycleOwner, Observer {
            transactionAdapter = TransactionAdapter(it.data)
            binding?.rvTransaction?.adapter = transactionAdapter
            binding?.rvTransaction?.adapter?.notifyDataSetChanged()
            Log.e("Rekap Fragment", "Succes: ${it.data}")
            binding?.rvTransaction?.visibility = View.VISIBLE
            binding?.progressBar?.visibility = View.GONE
        })

        transaksiVM.error.observe(viewLifecycleOwner, Observer {
            Log.e("Rekap Fragment", "Error: $it")
            Toast.makeText(requireActivity(), "Error: $it", Toast.LENGTH_SHORT).show()
        })

        transaksiVM.loading.observe(viewLifecycleOwner, Observer {
            binding?.progressBar?.visibility = View.VISIBLE
        })

        transaksiVM.getAllTransactions(idAccount, token)
        binding?.btnFab?.setOnClickListener {
            val intent = Intent(activity, AddTransactionActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}