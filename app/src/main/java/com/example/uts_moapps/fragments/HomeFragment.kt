package com.example.uts_moapps.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uts_moapps.R
import com.example.uts_moapps.RecommendationAdapter
import com.example.uts_moapps.TopPickAdapter

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Hubungkan fragment ke layout home_fragment.xml
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Top Picks RecyclerView
        val rvTop = view.findViewById<RecyclerView>(R.id.rvTopPicks)
        rvTop.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        rvTop.adapter = TopPickAdapter(listOf("A", "B", "C"))

        // Recommendation RecyclerView
        val rvRec = view.findViewById<RecyclerView>(R.id.rvRecommendation)
        rvRec.layoutManager = LinearLayoutManager(requireContext())
        rvRec.adapter = RecommendationAdapter(listOf("A", "B"))
    }
}