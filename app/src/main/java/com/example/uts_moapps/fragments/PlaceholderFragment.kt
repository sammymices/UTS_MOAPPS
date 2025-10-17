package com.example.uts_moapps.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.uts_moapps.R

class PlaceholderFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // simple layout: create small layout file fragment_placeholder.xml OR reuse home_fragment temporarily
        return inflater.inflate(R.layout.fragment_placeholder, container, false)
    }
}
