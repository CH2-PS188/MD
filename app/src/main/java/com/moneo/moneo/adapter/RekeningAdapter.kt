package com.moneo.moneo.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moneo.moneo.data.remote.model.RekeningsItem
import com.moneo.moneo.databinding.ItemAccountBinding

class RekeningAdapter(private var listRekening: List<RekeningsItem>) :
    RecyclerView.Adapter<RekeningAdapter.ListViewHolder>() {

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
        val rekening = listRekening[position]
        holder.bind(rekening)
    }
    override fun getItemCount(): Int = listRekening.size
}