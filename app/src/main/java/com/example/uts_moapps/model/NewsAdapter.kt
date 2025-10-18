package com.example.uts_moapps.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.uts_moapps.R
import com.example.uts_moapps.fragments.NewsFragmentDirections

data class NewsData(val title: String, val description: String, val imageRes: Int)

class NewsAdapter(private val newsList: List<NewsData>) :
    RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tvNewsTitle)
        val description: TextView = itemView.findViewById(R.id.tvNewsDesc)
        val image: ImageView = itemView.findViewById(R.id.ivNewsImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_news, parent, false)
        return NewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val news = newsList[position]
        holder.title.text = news.title
        holder.description.text = news.description
        holder.image.setImageResource(news.imageRes)

        holder.itemView.setOnClickListener {
            val action = NewsFragmentDirections.actionNavNewsToNewsDetailFragment(
                title = news.title,
                description = news.description,
                imageRes = news.imageRes
            )
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount() = newsList.size
}
