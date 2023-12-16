package com.moneo.moneo.ui.rekening

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.moneo.moneo.ViewModelFactory
import com.moneo.moneo.databinding.FragmentRekeningBinding

class RekeningFragment : Fragment() {

    private var _binding: FragmentRekeningBinding? = null
    private val binding get() = _binding

    private val viewModel: RekeningViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

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

    private fun setListRekening() {
        val rekeningAdapter = RekeningAdapter()

        viewModel.getAllRekening().observe(viewLifecycleOwner) { listRekening ->
            if (listRekening != null) {
                rekeningAdapter.submitList(listRekening)
            }
        }

        binding?.rvRekening?.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = rekeningAdapter
        }
    }

    private fun setFabClick() {
        binding?.btnFab?.setOnClickListener {
            val intent = Intent(activity, AddUpdateRekeningActivity::class.java)
            startActivity(intent)
        }
    }
}