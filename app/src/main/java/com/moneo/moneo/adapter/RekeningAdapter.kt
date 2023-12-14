package com.moneo.moneo.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moneo.moneo.data.local.rekening.Rekening
import com.moneo.moneo.databinding.ItemRekeningBinding
import com.moneo.moneo.ui.rekening.AddUpdateRekeningActivity


class RekeningAdapter : ListAdapter<Rekening, RekeningAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(private val binding: ItemRekeningBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(rekening: Rekening) {
            binding.apply {
                tvRekeningName.text = rekening.name
                tvRekeningBalance.text = rekening.balance.toString()
                cvAccount.setOnClickListener {
                    val intent = Intent(it.context, AddUpdateRekeningActivity::class.java)
                    intent.putExtra(AddUpdateRekeningActivity.EXTRA_ACCOUNT, rekening)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRekeningBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val account = getItem(position)
        holder.bind(account)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Rekening> =
            object : DiffUtil.ItemCallback<Rekening>() {
                override fun areContentsTheSame(oldItem: Rekening, newItem: Rekening): Boolean {
                    return oldItem.name == newItem.name
                }

                override fun areItemsTheSame(oldItem: Rekening, newItem: Rekening): Boolean {
                    return oldItem == newItem
                }
            }
    }

}