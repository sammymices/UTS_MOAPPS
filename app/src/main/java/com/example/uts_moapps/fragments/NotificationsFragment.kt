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
import com.example.uts_moapps.model.NotificationData
import com.google.android.material.appbar.MaterialToolbar

class NotificationsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)

        // 🔹 Setup toolbar
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbarNotifications)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp() // tombol back
        }

        // 🔹 Setup RecyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.rvNotifications)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Dummy data
        val notifications = listOf(
            NotificationData("Le_Monke's profile", "\"Halo, kamu lagi ngapain?\"", R.drawable.ic_profile),
            NotificationData("Gaben Official", "Your refund for Half-Life 3 has been processed.", R.drawable.ic_profile),
            NotificationData("Steam News", "Halloween Sale starts tomorrow!", R.drawable.ic_profile)
        )

        val adapter = NotificationAdapter(notifications)
        recyclerView.adapter = adapter

        return view
    }
}
