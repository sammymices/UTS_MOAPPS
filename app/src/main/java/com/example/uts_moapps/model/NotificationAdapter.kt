package com.example.uts_moapps.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.uts_moapps.R

// 🔹 Data class dipindahkan ke sini agar satu file dengan adapter
data class NotificationData(
    val title: String,
    val message: String,
    val iconRes: Int
)

class NotificationAdapter(
    private val items: MutableList<NotificationData>
) : RecyclerView.Adapter<NotificationAdapter.VH>() {

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgProfile: ImageView = itemView.findViewById(R.id.imgProfile)
        val txtTitle: TextView = itemView.findViewById(R.id.txtTitle)
        val txtMessage: TextView = itemView.findViewById(R.id.txtMessage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_notification, parent, false) // pastikan sesuai nama layout item-mu
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items[position]
        holder.imgProfile.setImageResource(item.iconRes)
        holder.txtTitle.text = item.title
        holder.txtMessage.text = item.message
    }

    override fun getItemCount(): Int = items.size

    // 🔹 Untuk update data (misalnya dari NotificationCenter)
    fun submit(newItems: List<NotificationData>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
        }
}