package com.example.uts_moapps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Top Picks RecyclerView
        val rvTop = findViewById<RecyclerView>(R.id.rvTopPicks)
        rvTop.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        rvTop.adapter = TopPickAdapter(listOf("A", "B", "C"))

        // Recommendation RecyclerView
        val rvRec = findViewById<RecyclerView>(R.id.rvRecommendation)
        rvRec.layoutManager = LinearLayoutManager(this)
        rvRec.adapter = RecommendationAdapter(listOf("A", "B"))
    }
}
