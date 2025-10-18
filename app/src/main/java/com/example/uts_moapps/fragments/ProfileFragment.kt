package com.example.uts_moapps.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.uts_moapps.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // 🔹 Ambil elemen-elemen dari layout
        val menuEditProfile = view.findViewById<LinearLayout>(R.id.menuEditProfile)
        val menuNotification = view.findViewById<LinearLayout>(R.id.menuNotification)
        val menuMyGames = view.findViewById<LinearLayout>(R.id.menuMyGames)
        val menuAbout = view.findViewById<LinearLayout>(R.id.menuAbout)
        val btnSignOut = view.findViewById<View>(R.id.btnSignOut)
        val profileContainer = view.findViewById<View>(R.id.profileContainer)

        // 🔹 Terapkan dynamic padding agar aman dari notch / status bar
        ViewCompat.setOnApplyWindowInsetsListener(profileContainer) { v, insets ->
            val statusBarHeight = insets.getInsets(androidx.core.view.WindowInsetsCompat.Type.statusBars()).top
            v.updatePadding(top = statusBarHeight + 24) // tambahkan jarak ekstra 24dp agar tidak terlalu nempel
            insets
        }

        // 🔹 Listener untuk setiap menu
        menuEditProfile.setOnClickListener {
            Toast.makeText(requireContext(), "Edit Profile clicked", Toast.LENGTH_SHORT).show()
        }

        menuNotification.setOnClickListener {
            Toast.makeText(requireContext(), "Notification clicked", Toast.LENGTH_SHORT).show()
        }

        menuMyGames.setOnClickListener {
            findNavController().navigate(R.id.action_nav_profile_to_myGamesFragment)
        }

        menuAbout.setOnClickListener {
            Toast.makeText(requireContext(), "About clicked", Toast.LENGTH_SHORT).show()
        }

        btnSignOut.setOnClickListener {
            Toast.makeText(requireContext(), "Signed Out", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
