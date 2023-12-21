package com.moneo.moneo.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moneo.moneo.data.remote.model.ListRekapItem
import com.moneo.moneo.databinding.ItemRekapBinding

class RekapAdapter(private var listRekap: List<ListRekapItem>) :
    RecyclerView.Adapter<RekapAdapter.ListViewHolder>() {
    inner class ListViewHolder(private val view: ItemRekapBinding) :
        RecyclerView.ViewHolder(view.root) {
        @SuppressLint("SetTextI18n")
        fun bind(rekap: ListRekapItem) {
            Log.d("RekapAdapter", "Binding item: $rekap")
            with(view) {
                tvPemasukan.text = "Pemasukan"
                tvRataPemasukan.text = "rata-rata perhari"
                tvPengeluaran.text = "Pengeluaran"
                tvRataPengeluaran.text = "rata-rata perhari"
                tvSelisih.text = "Selisih"
                tvTotal.text = "Total"
                tvTanggal.text = rekap.dateRange
                tvAngkaPengeluaran.text = rekap.totalExpenses
                tvAngkaRataPengeluaran.text = rekap.averageDailyExpenses
                tvAngkaPemasukan.text = rekap.totalIncome
                tvAngkaRataPemasukan.text = rekap.averageDailyIncome
                tvAngkaSelisih.text = rekap.difference
                tvAngkaTotal.text = rekap.total.toString()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = ItemRekapBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val rekap = listRekap[position]
        holder.bind(rekap)
    }
    override fun getItemCount(): Int = listRekap.size

}