package com.imasha.hydrateme.adapters

import android.content.ClipData
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imasha.hydrateme.data.model.Cup
import com.imasha.hydrateme.databinding.CupLayoutBinding

class CupAdapter(
    private val items: List<Cup>,
    private val onItemClick: (Cup) -> Unit
) : RecyclerView.Adapter<CupAdapter.CupViewHolder>() {

    inner class CupViewHolder(val binding: CupLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Cup) {
            binding.cupSize.text = "${item.size}ml"
            binding.root.setOnClickListener {
                onItemClick(item) // Trigger the click callback
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CupViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CupLayoutBinding.inflate(inflater, parent, false)
        return CupViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CupViewHolder, position: Int) {
        val item = items[position]
        holder.binding.cupSize.text = item.size.toString() + "ml"

        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}