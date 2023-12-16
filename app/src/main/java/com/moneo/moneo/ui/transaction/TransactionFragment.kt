package com.moneo.moneo.ui.transaction

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.moneo.moneo.ViewModelFactory
import com.moneo.moneo.databinding.FragmentTransactionBinding

class TransactionFragment : Fragment() {

    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding

    private val viewModel: TransactionViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

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

        setListTransaction()
        setFabClick()
    }

    private fun setListTransaction() {
        val transactionAdapter = TransactionAdapter()

        viewModel.getAllTransaction().observe(viewLifecycleOwner) { result ->
            if (result != null) {
                transactionAdapter.submitList(result)
            }
        }

        binding?.rvTransaction?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = transactionAdapter
        }
    }

    private fun setFabClick() {
        binding?.btnFab?.setOnClickListener {
            val intent = Intent(activity, AddUpdateTransactionActivity::class.java)
            startActivity(intent)
        }
    }


}