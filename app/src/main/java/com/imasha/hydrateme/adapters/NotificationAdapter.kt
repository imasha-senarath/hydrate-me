package com.imasha.hydrateme.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.imasha.hydrateme.R
import com.imasha.hydrateme.data.model.Notification
import com.imasha.hydrateme.databinding.RowNotificationBinding

class NotificationAdapter(
    private val items: List<Notification>,
    private val context: Context,
) : RecyclerView.Adapter<NotificationAdapter.RecordViewHolder>() {

    inner class RecordViewHolder(private val binding: RowNotificationBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Notification) {
            binding.title.text = item.title
            binding.message.text = item.message
            binding.dateTime.text = context.getString(R.string.date_time, item.date, item.time)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RowNotificationBinding.inflate(inflater, parent, false)
        return RecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}