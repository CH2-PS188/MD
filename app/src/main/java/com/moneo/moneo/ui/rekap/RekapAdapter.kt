package com.moneo.moneo.ui.rekap

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.moneo.moneo.data.local.rekap.Rekap
import com.moneo.moneo.data.remote.response.Summary
import com.moneo.moneo.databinding.ItemRekapBinding

class RekapAdapter(private var listRekap: List<Summary>) :
    RecyclerView.Adapter<RekapAdapter.ListViewHolder>() {
    inner class ListViewHolder(private val view: ItemRekapBinding) :
        RecyclerView.ViewHolder(view.root) {
        @SuppressLint("SetTextI18n")
        fun bind(rekap: Summary) {
            Log.d("RekapAdapter", "Binding item: $rekap")
            with(view) {
                tvPemasukan.text = "Pemasukan"
                tvRataPemasukan.text = "rata-rata perhari"
                tvPengeluaran.text = "Pengeluaran"
                tvRataPengeluaran.text = "rata-rata perhari"
                tvSelisih.text = "Selisih"
                tvTanggal.text = rekap.date
                tvAngkaPengeluaran.text = rekap.totalExpenses
                tvAngkaPemasukan.text = rekap.totalIncome
                tvAngkaSelisih.text = rekap.difference
                tvAngkaRataPemasukan.text = rekap.averageIncome
                tvAngkaRataPengeluaran.text = rekap.averageExpenses
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