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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.moneo.moneo.adapter.RekeningAdapter
import com.moneo.moneo.adapter.TransactionAdapter
import com.moneo.moneo.databinding.FragmentAccountBinding
import com.moneo.moneo.ui.factory.RekeningFactory
import com.moneo.moneo.ui.transaction.AddTransactionActivity

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var rekeningAdapter: RekeningAdapter
    private lateinit var rekeningVM: AccountViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding?.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: RekeningFactory = RekeningFactory.getInstance(requireActivity())
        rekeningVM = ViewModelProvider(this, factory)[AccountViewModel::class.java]

        firebaseAuth = FirebaseAuth.getInstance()

        rekeningAdapter = RekeningAdapter(emptyList())
        binding?.rvAccounts?.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
        binding?.rvAccounts?.adapter = rekeningAdapter


        val idAccount = firebaseAuth.currentUser!!.uid
        val token = firebaseAuth.currentUser!!.uid

        rekeningVM.success.observe(viewLifecycleOwner, Observer {
            rekeningAdapter = RekeningAdapter(it.rekenings)
            binding?.tvAccountBalance?.text = it.totalSaldo
            binding?.rvAccounts?.adapter = rekeningAdapter
            binding?.rvAccounts?.adapter?.notifyDataSetChanged()
            Log.e("Rekap Fragment", "Succes: ${it.rekenings}")
            binding?.rvAccounts?.visibility = View.VISIBLE
            binding?.progressBar?.visibility = View.GONE
        })

        rekeningVM.error.observe(viewLifecycleOwner, Observer {
            Log.e("Rekap Fragment", "Error: $it")
            Toast.makeText(requireActivity(), "Error: $it", Toast.LENGTH_SHORT).show()
        })

        rekeningVM.loading.observe(viewLifecycleOwner, Observer {
            binding?.progressBar?.visibility = View.VISIBLE
        })

        rekeningVM.getAllRekening(idAccount, token)
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