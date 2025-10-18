package com.example.uts_moapps.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.uts_moapps.R

class NewsDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_detail, container, false)

        val title = view.findViewById<TextView>(R.id.tvDetailTitle)
        val description = view.findViewById<TextView>(R.id.tvDetailDescription)
        val image = view.findViewById<ImageView>(R.id.ivDetailImage)

        val args = requireArguments()
        title.text = args.getString("title")
        description.text = args.getString("description")
        image.setImageResource(args.getInt("imageRes"))

        return view
    }
}
