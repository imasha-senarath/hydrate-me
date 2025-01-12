package com.imasha.hydrateme.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imasha.hydrateme.R
import com.imasha.hydrateme.data.model.Record
import com.imasha.hydrateme.databinding.RowHistoryBinding

class HistoryAdapter(
    private val items: List<Record>,
    private val context: Context,
) : RecyclerView.Adapter<HistoryAdapter.RecordViewHolder>() {

    inner class RecordViewHolder(private val binding: RowHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Record) {
            binding.total.text = context.getString(R.string.size_ml, item.size)
            binding.date.text = item.date
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowHistoryBinding.inflate(inflater, parent, false)
        return RecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}