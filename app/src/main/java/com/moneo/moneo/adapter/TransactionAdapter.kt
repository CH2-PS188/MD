package com.moneo.moneo.adapter


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moneo.moneo.data.remote.model.TransactionItem
import com.moneo.moneo.databinding.ItemTransactionBinding

class TransactionAdapter(private val transactions: List<TransactionItem>) :
    RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    inner class ViewHolder(private val view: ItemTransactionBinding) : RecyclerView.ViewHolder(view.root){
        fun bind(transactions: TransactionItem){
            with(view){
                tvItemTitle.text = transactions.title
                tvItemCategory.text = transactions.category
                tvItemDescription.text = transactions.description
                tvItemTotal.text = transactions.total
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val transaction = transactions[position]
        holder.bind(transaction)
    }

    override fun getItemCount(): Int {
        return transactions.size
    }
}