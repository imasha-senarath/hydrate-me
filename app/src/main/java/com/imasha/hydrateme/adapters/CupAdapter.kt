package com.imasha.hydrateme.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imasha.hydrateme.R
import com.imasha.hydrateme.data.model.Cup
import com.imasha.hydrateme.databinding.RowCupBinding

class CupAdapter(
    private val items: List<Cup>,
    private val context: Context,
    private val onItemClick: (Cup) -> Unit
) : RecyclerView.Adapter<CupAdapter.CupViewHolder>() {

    inner class CupViewHolder(private val binding: RowCupBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Cup) {
            binding.cupSize.text = context.getString(R.string.size_ml, item.size)
            binding.root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CupViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowCupBinding.inflate(inflater, parent, false)
        return CupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CupViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}