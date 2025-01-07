package com.imasha.hydrateme.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imasha.hydrateme.data.model.Cup
import com.imasha.hydrateme.data.model.Record
import com.imasha.hydrateme.databinding.RowRecordBinding

class RecordAdapter(
    private val items: List<Record>,
    private val onDeleteClick: (Record) -> Unit
) : RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {

    inner class RecordViewHolder(val binding: RowRecordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Record) {
            binding.cupSize.text = "${item.size}    ml"
            binding.time.text = item.time
            binding.btnDelete.setOnClickListener {
                onDeleteClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowRecordBinding.inflate(inflater, parent, false)
        return RecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        val item = items[position]
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    /*fun updateList(newItems: List<Record>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }*/
}