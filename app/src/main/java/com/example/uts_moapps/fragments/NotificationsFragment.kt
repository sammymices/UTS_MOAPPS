package com.example.uts_moapps.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.uts_moapps.R
import com.example.uts_moapps.model.NotificationAdapter
import com.example.uts_moapps.model.NotificationCenter
import com.google.android.material.appbar.MaterialToolbar

class NotificationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)

        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbarNotifications)
        toolbar.setNavigationOnClickListener { findNavController().navigateUp() }

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvNotifications)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = NotificationAdapter(mutableListOf())
        recyclerView.adapter = adapter

        // Observe perubahan data dari NotificationCenter
        NotificationCenter.notifications.observe(viewLifecycleOwner) { list ->
            adapter.submit(list)
        }

        return view
    }
}