package com.example.uts_moapps.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uts_moapps.R
import com.example.uts_moapps.model.NewsAdapter
import com.example.uts_moapps.model.NewsData
import android.widget.EditText

class NewsFragment : Fragment() {

    private lateinit var adapter: NewsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsList: List<NewsData>
    private lateinit var searchBar: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)
        recyclerView = view.findViewById(R.id.recyclerNews)
        searchBar = view.findViewById(R.id.searchNews)

        newsList = listOf(
            NewsData("Battle Pass Season 21: “Brave Archer”", "New rewards and challenges await!", R.drawable.sample1),
            NewsData("Screenshot Competition – High Horizons!", "Show your best screenshots!", R.drawable.sample2),
            NewsData("New Vehicle Line Announced", "Developers reveal their next major update.", R.drawable.sample3),
            NewsData("New Vehicle Line Announced", "Developers reveal their next major update.", R.drawable.sample4),
            NewsData("Map Expansion Incoming!", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", R.drawable.sample5),
            NewsData("Weekend Bonus Event", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", R.drawable.sample6),
            NewsData("Community Skin Contest", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", R.drawable.sample7),
            NewsData("Server Maintenance Notice", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", R.drawable.sample8)
        )

        adapter = NewsAdapter(newsList)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        // fitur pencarian sederhana
        searchBar.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val filtered = newsList.filter {
                    it.title.contains(s.toString(), ignoreCase = true) ||
                            it.description.contains(s.toString(), ignoreCase = true)
                }
                recyclerView.adapter = NewsAdapter(filtered)
            }
        })

        return view
    }
}