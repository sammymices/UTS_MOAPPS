package com.example.uts_moapps.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uts_moapps.R
import com.example.uts_moapps.model.NewsAdapter
import com.example.uts_moapps.model.NewsData

class NewsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)

        // Temukan RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerNews)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Isi data dummy (bisa nanti diganti dari API)
        val newsList = listOf(
            NewsData("Update Baru Game XYZ!", "Patch 2.0 kini hadir dengan fitur baru dan balancing."),
            NewsData("Diskon Mingguan", "Jangan lewatkan diskon besar-besaran hingga 70%!"),
            NewsData("Game Baru Dirilis", "Developer ABC baru saja meluncurkan game survival terbarunya.")
        )

        recyclerView.adapter = NewsAdapter(newsList)
        return view
    }
}