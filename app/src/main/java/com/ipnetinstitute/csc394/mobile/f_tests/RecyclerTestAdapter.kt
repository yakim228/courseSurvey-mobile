package com.ipnetinstitute.csc394.mobile.f_tests

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ipnetinstitute.csc394.mobile.R
import kotlinx.android.synthetic.main.recycler_test_item.view.*

class RecyclerTestAdapter(var textList: List<String>): RecyclerView.Adapter<RecyclerTestAdapter.ViewHolder>() {
    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
        val textView = itemView.findViewById<TextView>(R.id.textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_test_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return textList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val text = textList[position]
        holder.textView.text = text
    }
}