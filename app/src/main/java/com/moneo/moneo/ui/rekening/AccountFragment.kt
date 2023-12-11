package com.moneo.moneo.ui.rekening

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.moneo.moneo.R
import com.moneo.moneo.ViewModelFactory
import com.moneo.moneo.adapter.AccountAdapter
import com.moneo.moneo.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(requireActivity())
        val viewModel: AccountViewModel by viewModels {
            factory
        }

        val accountsAdapter = AccountAdapter()

        viewModel.getAllAccounts().observe(viewLifecycleOwner) { listAccounts ->
            if (listAccounts != null) {
                accountsAdapter.submitList(listAccounts)
            }
        }

        binding?.rvAccounts?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = accountsAdapter
        }

        binding?.btnFab?.setOnClickListener {
            val intent = Intent(activity, AddUpdateAccountActivity::class.java)
            startActivity(intent)
        }

    }
}