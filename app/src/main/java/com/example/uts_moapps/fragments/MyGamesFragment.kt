package com.example.uts_moapps.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uts_moapps.R
import com.example.uts_moapps.TopPickAdapter

class MyGamesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_games, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerMyGames)
        val tvEmpty = view.findViewById<TextView>(R.id.tvEmptyGames)
        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)

        val myGames = com.example.uts_moapps.MyGamesData.getMyGames()

        if (myGames.isEmpty()) {
            recyclerView.visibility = View.GONE
            tvEmpty.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.VISIBLE
            tvEmpty.visibility = View.GONE

            recyclerView.layoutManager = GridLayoutManager(requireContext(), 2) // ✅ 2 kolom
            recyclerView.adapter = TopPickAdapter(myGames)
        }

        // ✅ Tombol back
        btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
    }
}
