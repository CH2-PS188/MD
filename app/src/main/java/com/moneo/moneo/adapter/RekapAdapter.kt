package com.moneo.moneo.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.moneo.moneo.data.remote.response.ListRekapItem
import com.moneo.moneo.databinding.ItemRekapBinding

class RekapAdapter : ListAdapter<ListRekapItem, RekapAdapter.ListViewHolder>(DIFF_CALLBACK) {

    private val listRekap = ArrayList<ListRekapItem>()

    inner class ListViewHolder(private val view: ItemRekapBinding) :
        RecyclerView.ViewHolder(view.root) {
        fun bind(rekap: ListRekapItem) {
            Log.d("RekapAdapter", "Binding item: $rekap")
            with(view) {
                tvTanggal.text = rekap.date
                tvAngkaPengeluaran.text = rekap.totalIncome
                tvAngkaPemasukan.text = rekap.totalExpenses
                tvAngkaSelisih.text = rekap.difference
                tvAngkaRataPengeluaran.text = rekap.startDate
                tvAngkaRataPemasukan.text = rekap.endDate
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = ItemRekapBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listRekap[position])
    }
    override fun getItemCount(): Int = listRekap.size

    companion object{
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ListRekapItem> =
            object : DiffUtil.ItemCallback<ListRekapItem>(){
                override fun areItemsTheSame(oldItem: ListRekapItem, newItem: ListRekapItem): Boolean {
                    return oldItem.date == newItem.date
                }
                override fun areContentsTheSame(oldItem: ListRekapItem, newItem: ListRekapItem): Boolean {
                    return oldItem == newItem
                }
            }
    }

}