package com.moneo.moneo.ui.transaction

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
import com.moneo.moneo.databinding.FragmentTransactionBinding
import com.moneo.moneo.ui.onboarding.OnBoardingActivity

class TransactionFragment : Fragment() {

    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding

    private val viewModel: TransactionViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()

        authStateListener = FirebaseAuth.AuthStateListener { auth ->
            val user = auth.currentUser
            if (user == null) {
                redirectToLoginScreen()
            }
        }

        setListTransaction()
        setFabClick()
    }

    private fun setListTransaction() {

        val idAccount = firebaseAuth.currentUser?.uid
        val token = firebaseAuth.currentUser?.uid

        if (idAccount != null && token != null) {
            val transactionAdapter = TransactionAdapter()

            viewModel.getAllTransaction(idAccount, token)

            viewModel.getTransactionResult()?.observe(viewLifecycleOwner) { result ->
                if (result != null) {
                    when (result) {
                        is Result.Loading -> {
                            binding?.progressBar?.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding?.progressBar?.visibility = View.GONE
                            val listTransaction = result.data
                            transactionAdapter.submitList(listTransaction)
                            binding?.rvTransaction?.adapter = transactionAdapter
                            Log.d("TRANSACTION-DATA", "$listTransaction")
                        }
                        is Result.Error -> {
                            binding?.progressBar?.visibility = View.GONE
                            Toast.makeText(
                                context,
                                "Terjadi kesalahan" + result.error,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }

            binding?.rvTransaction?.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = transactionAdapter
            }
        } else {
            // Redirect to onboarding screen if idAccount or token is null
            redirectToLoginScreen()
        }
    }

    private fun setFabClick() {
        binding?.btnFab?.setOnClickListener {
            val intent = Intent(activity, AddUpdateTransactionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun redirectToLoginScreen() {
        val intent = Intent(activity, OnBoardingActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        activity?.finish()
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    override fun onStop() {
        super.onStop()
        firebaseAuth.removeAuthStateListener(authStateListener)
    }


}