package com.imasha.hydrateme.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imasha.hydrateme.R
import com.imasha.hydrateme.data.model.Cup
import com.imasha.hydrateme.data.model.Record
import com.imasha.hydrateme.databinding.RowRecordBinding
import com.imasha.hydrateme.utils.DateUtils.convertTo12

class RecordAdapter(
    private val items: List<Record>,
    private val context: Context,
    private val onDeleteClick: (Record) -> Unit
) : RecyclerView.Adapter<RecordAdapter.RecordViewHolder>() {

    inner class RecordViewHolder(private val binding: RowRecordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Record) {
            binding.cupSize.text = context.getString(R.string.size_ml, item.size)
            binding.time.text = convertTo12(item.time)
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
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}