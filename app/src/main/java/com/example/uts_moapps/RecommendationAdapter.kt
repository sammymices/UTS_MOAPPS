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

class RecommendationAdapter(private val gameList: List<GameModel>) :
    RecyclerView.Adapter<RecommendationAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgGame: ImageView = view.findViewById(R.id.imgGameRec)
        val tvTitle: TextView = view.findViewById(R.id.tvTitleRec)
        val tvPrice: TextView = view.findViewById(R.id.tvPriceRec)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recommendation, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = gameList[position]

        holder.imgGame.setImageResource(game.imageResId)
        holder.tvTitle.text = game.title
        holder.tvPrice.text = game.price

        // ✅ Klik item untuk buka detail
        holder.itemView.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("game", game)
            it.findNavController().navigate(R.id.nav_game_detail, bundle)
        }
    }

    override fun getItemCount(): Int = gameList.size
}
