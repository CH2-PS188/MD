package com.moneo.moneo.ui.rekening

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moneo.moneo.R
import com.moneo.moneo.data.local.rekening.Rekening
import com.moneo.moneo.databinding.ItemRekeningBinding


class RekeningAdapter : ListAdapter<Rekening, RekeningAdapter.ViewHolder>(DIFF_CALLBACK) {

    class ViewHolder(private val binding: ItemRekeningBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(rekening: Rekening) {
            binding.apply {
                tvRekeningName.text = rekening.name
                if (rekening.balance < 0) {
                    tvRekeningBalance.setTextColor(itemView.context.resources.getColor(R.color.red))
                    tvRekeningBalance.text = rekening.balance.toString()
                } else {
                    tvRekeningBalance.setTextColor(itemView.context.resources.getColor(R.color.black))
                    tvRekeningBalance.text = rekening.balance.toString()
                }
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