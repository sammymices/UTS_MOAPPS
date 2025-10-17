package com.example.uts_moapps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.uts_moapps.model.GameModel

class TopPickAdapter(private val items: List<GameModel>) :
    RecyclerView.Adapter<TopPickAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgGame: ImageView = view.findViewById(R.id.imgGameTop)
        val tvTitle: TextView = view.findViewById(R.id.tvTitleTop)
        val tvPrice: TextView = view.findViewById(R.id.tvPriceTop)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_top_pick, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = items[position]
        holder.imgGame.setImageResource(game.imageResId)
        holder.tvTitle.text = game.title
        holder.tvPrice.text = game.price

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("game", game)
            it.findNavController().navigate(R.id.nav_game_detail, bundle)
        }

    }

    override fun getItemCount(): Int = items.size
}
