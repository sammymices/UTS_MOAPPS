package com.example.uts_moapps.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.uts_moapps.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Menu Item IDs
        val menuEditProfile = view.findViewById<LinearLayout>(R.id.menuEditProfile)
        val menuNotification = view.findViewById<LinearLayout>(R.id.menuNotification)
        val menuMyGames = view.findViewById<LinearLayout>(R.id.menuMyGames)
        val menuAbout = view.findViewById<LinearLayout>(R.id.menuAbout)

        // Button Logout
        val btnSignOut = view.findViewById<View>(R.id.btnSignOut)

        // Listener Menu
        menuEditProfile.setOnClickListener {
            Toast.makeText(requireContext(), "Edit Profile clicked", Toast.LENGTH_SHORT).show()
        }
        menuNotification.setOnClickListener {
            Toast.makeText(requireContext(), "Notification clicked", Toast.LENGTH_SHORT).show()
        }
        menuMyGames.setOnClickListener {
            Toast.makeText(requireContext(), "My Games clicked", Toast.LENGTH_SHORT).show()
        }
        menuAbout.setOnClickListener {
            Toast.makeText(requireContext(), "About clicked", Toast.LENGTH_SHORT).show()
        }

        // Logout
        btnSignOut.setOnClickListener {
            Toast.makeText(requireContext(), "Signed Out", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
