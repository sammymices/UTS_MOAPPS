package com.example.uts_moapps.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uts_moapps.R
import com.example.uts_moapps.RecommendationAdapter
import com.example.uts_moapps.TopPickAdapter
import com.example.uts_moapps.model.GameData

class HomeFragment : Fragment() {

    private lateinit var rvTop: RecyclerView
    private lateinit var rvRec: RecyclerView
    private lateinit var rvSearch: RecyclerView
    private lateinit var searchAdapter: RecommendationAdapter
    private lateinit var etSearch: EditText
    private lateinit var btnClear: ImageView
    private lateinit var tvNoResult: TextView
    private lateinit var imgBanner: ImageView
    private lateinit var tvTopGames: TextView
    private lateinit var tvFallSeason: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        // 🔹 Inisialisasi komponen
        rvTop = view.findViewById(R.id.rvTopPicks)
        rvRec = view.findViewById(R.id.rvRecommendation)
        rvSearch = view.findViewById(R.id.rvSearchResult)
        etSearch = view.findViewById(R.id.etSearch)
        btnClear = view.findViewById(R.id.btnClear)
        tvNoResult = view.findViewById(R.id.tvNoResult)
        imgBanner = view.findViewById(R.id.imgBanner)
        tvTopGames = view.findViewById(R.id.tvTopGames)
        tvFallSeason = view.findViewById(R.id.tvFallSeason)

        // 🔹 Setup RecyclerView
        val rvTop = view.findViewById<RecyclerView>(R.id.rvTopPicks)
        val gridLayoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.HORIZONTAL, false)
        rvTop.layoutManager = gridLayoutManager
        rvTop.adapter = TopPickAdapter(GameData.topGames)

        rvRec.layoutManager = LinearLayoutManager(requireContext())
        rvRec.adapter = RecommendationAdapter(GameData.recommendedGames)

        rvSearch.layoutManager = LinearLayoutManager(requireContext())

        // 🔹 Tombol clear search
        btnClear.setOnClickListener {
            etSearch.text.clear()
        }

        // 🔹 Fungsi pencarian
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim().lowercase()
                btnClear.visibility = if (query.isNotEmpty()) View.VISIBLE else View.GONE

                if (query.isEmpty()) {
                    // Kembalikan tampilan normal
                    rvSearch.visibility = View.GONE
                    tvNoResult.visibility = View.GONE
                    rvTop.visibility = View.VISIBLE
                    rvRec.visibility = View.VISIBLE
                    imgBanner.visibility = View.VISIBLE
                    tvTopGames.visibility = View.VISIBLE
                    tvFallSeason.visibility = View.VISIBLE
                } else {
                    val allGames = GameData.topGames + GameData.recommendedGames
                    val filtered = allGames.filter { it.title.lowercase().contains(query) }

                    // Sembunyikan bagian lain
                    rvTop.visibility = View.GONE
                    rvRec.visibility = View.GONE
                    imgBanner.visibility = View.GONE
                    tvTopGames.visibility = View.GONE
                    tvFallSeason.visibility = View.GONE

                    if (filtered.isEmpty()) {
                        tvNoResult.visibility = View.VISIBLE
                        rvSearch.visibility = View.GONE
                    } else {
                        tvNoResult.visibility = View.GONE
                        rvSearch.visibility = View.VISIBLE
                        searchAdapter = RecommendationAdapter(filtered)
                        rvSearch.adapter = searchAdapter
                    }
                }
            }
        })
    }
}
