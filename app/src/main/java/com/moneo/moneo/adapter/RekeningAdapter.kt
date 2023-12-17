package com.moneo.moneo.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moneo.moneo.data.remote.model.RekeningsItem
import com.moneo.moneo.databinding.ItemAccountBinding

class RekeningAdapter: ListAdapter<RekeningsItem, RekeningAdapter.ListViewHolder>(DIFF_CALLBACK) {

    inner class ListViewHolder(private val view: ItemAccountBinding) :
        RecyclerView.ViewHolder(view.root) {
        @SuppressLint("SetTextI18n")
        fun bind(rekening: RekeningsItem) {
            Log.d("RekapAdapter", "Binding item: $rekening")
            with(view) {
                tvAccountName.text = rekening.judul
                tvAccountBalance.text = rekening.saldo
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = ItemAccountBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val rekening = getItem(position)
        holder.bind(rekening)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RekeningsItem>() {
            override fun areItemsTheSame(oldItem: RekeningsItem, newItem: RekeningsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: RekeningsItem, newItem: RekeningsItem): Boolean {
                return oldItem.judul == newItem.judul
            }
        }
    }
}