package com.moneo.moneo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moneo.moneo.data.local.transaction.Transaction
import com.moneo.moneo.databinding.ItemTransactionBinding


class TransactionAdapter : ListAdapter<Transaction, TransactionAdapter.ViewHolder>(DIFF_CALLBACK){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = getItem(position)
        holder.bind(transaction)
    }

    class ViewHolder(val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(transaction: Transaction) {
            binding.apply {
                tvItemTitle.text = transaction.title
                tvItemCategory.text = transaction.category
                tvItemTotal.text = "Rp ${transaction.total}"
                tvItemDescription.text = transaction.description
            }
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Transaction> =
            object : DiffUtil.ItemCallback<Transaction>() {
                override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
                    return oldItem.title == newItem.title
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
                    return oldItem == newItem
                }
            }
    }
}