package com.moneo.moneo.ui.transaction

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moneo.moneo.R
import com.moneo.moneo.data.local.transaction.Transaction
import com.moneo.moneo.databinding.ItemTransactionBinding


class TransactionAdapter : ListAdapter<Transaction, TransactionAdapter.ViewHolder>(DIFF_CALLBACK){

    class ViewHolder(private val binding: ItemTransactionBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(transaction: Transaction) {
            binding.apply {
                tvItemDate.text = transaction.date
                tvItemTitle.text = transaction.title
                tvItemCategory.text = transaction.category
                if (transaction.type == "pengeluaran") {
                    tvItemTotal.setTextColor(itemView.context.resources.getColor(R.color.red))
                    tvItemTotal.text = "Rp ${transaction.total}"
                } else if (transaction.type == "pemasukan") {
                    tvItemTotal.setTextColor(itemView.context.resources.getColor(R.color.blue))
                    tvItemTotal.text = "Rp ${transaction.total}"
                }
                tvItemDescription.text = transaction.description
                cvTransaction.setOnClickListener {
                    val intent = Intent(it.context, AddUpdateTransactionActivity::class.java)
                    intent.putExtra(AddUpdateTransactionActivity.EXTRA_TRANSACTION, transaction)
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