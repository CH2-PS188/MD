package com.moneo.moneo.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moneo.moneo.data.local.account.Account
import com.moneo.moneo.databinding.ItemAccountBinding
import com.moneo.moneo.ui.rekening.AddUpdateAccountActivity


class AccountAdapter : ListAdapter<Account, AccountAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(val binding: ItemAccountBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(account: Account) {
            binding.apply {
                tvAccountName.text = account.name
                tvAccountBalance.text = account.balance.toString()
                cvAccount.setOnClickListener {
                    val intent = Intent(it.context, AddUpdateAccountActivity::class.java)
                    intent.putExtra(AddUpdateAccountActivity.EXTRA_ACCOUNT, account)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAccountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val account = getItem(position)
        holder.bind(account)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Account> =
            object : DiffUtil.ItemCallback<Account>() {
                override fun areContentsTheSame(oldItem: Account, newItem: Account): Boolean {
                    return oldItem.name == newItem.name
                }

                override fun areItemsTheSame(oldItem: Account, newItem: Account): Boolean {
                    return oldItem == newItem
                }
            }
    }

}