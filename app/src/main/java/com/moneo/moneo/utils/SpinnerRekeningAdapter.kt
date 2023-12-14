package com.moneo.moneo.utils

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.moneo.moneo.data.local.rekening.Rekening

class SpinnerRekeningAdapter(context: Context, resource: Int, rekenings: List<Rekening>) :
    ArrayAdapter<Rekening>(context, resource, rekenings) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val account = getItem(position)
        val view = super.getView(position, convertView, parent)
        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = account?.name ?: "Silahkan tambah rekening terlebih dahulu"
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }
}