package com.moneo.moneo.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moneo.moneo.data.local.transaction.Transaction
import com.moneo.moneo.databinding.ItemTransactionBinding
import com.moneo.moneo.ui.rekening.AddUpdateAccountActivity
import com.moneo.moneo.ui.transaction.AddTransactionActivity


class TransactionAdapter : ListAdapter<Transaction, TransactionAdapter.ViewHolder>(DIFF_CALLBACK){

    class ViewHolder(val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(transaction: Transaction) {
            binding.apply {
                tvItemTitle.text = transaction.title
                tvItemCategory.text = transaction.category
                tvItemTotal.text = "Rp ${transaction.total}"
                tvItemDescription.text = transaction.description
                cvTransaction.setOnClickListener {
                    val intent = Intent(it.context, AddTransactionActivity::class.java)
                    intent.putExtra(AddTransactionActivity.EXTRA_TRANSACTION, transaction)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = getItem(position)
        holder.bind(transaction)
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