package com.example.uts_moapps.fragments

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.uts_moapps.R
import com.example.uts_moapps.UserPreferences

class ProfileFragment : Fragment() {

    private lateinit var prefs: UserPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        prefs = UserPreferences(requireContext())

        val menuEditProfile = view.findViewById<LinearLayout>(R.id.menuEditProfile)
        val menuNotification = view.findViewById<LinearLayout>(R.id.menuNotification)
        val menuMyGames = view.findViewById<LinearLayout>(R.id.menuMyGames)
        val menuAbout = view.findViewById<LinearLayout>(R.id.menuAbout)
        val btnSignOut = view.findViewById<View>(R.id.btnSignOut)
        val profileContainer = view.findViewById<View>(R.id.profileContainer)

        // Apply Safe Area Padding
        ViewCompat.setOnApplyWindowInsetsListener(profileContainer) { v, insets ->
            val statusBarHeight = insets.getInsets(androidx.core.view.WindowInsetsCompat.Type.statusBars()).top
            v.updatePadding(top = statusBarHeight + 24)
            insets
        }

        // ✅ Tombol Edit Profile → buka EditProfileFragment
        menuEditProfile.setOnClickListener {
            findNavController().navigate(R.id.action_nav_profile_to_editProfileFragment)
        }

        menuNotification.setOnClickListener {
            findNavController().navigate(R.id.action_nav_profile_to_notificationsFragment)
        }

        menuMyGames.setOnClickListener {
            findNavController().navigate(R.id.action_nav_profile_to_myGamesFragment)
        }

        menuAbout.setOnClickListener {
            findNavController().navigate(R.id.action_nav_profile_to_aboutUsFragment)
        }

        btnSignOut.setOnClickListener {
            Toast.makeText(requireContext(), "Signed Out", Toast.LENGTH_SHORT).show()
        }

        loadProfileData(view)
        return view
    }

    override fun onResume() {
        super.onResume()
        view?.let { loadProfileData(it) } // refresh saat kembali dari Edit Profile
    }

    private fun loadProfileData(view: View) {
        val tvUsername = view.findViewById<TextView>(R.id.tvUsername)
        val imgProfile = view.findViewById<ImageView>(R.id.imgProfile)

        // Update Username
        tvUsername.text = prefs.getUsername()

        // Load Profile Image
        val uri = prefs.getProfileImage()

        if (uri.isNullOrEmpty()) {
            imgProfile.setImageResource(R.drawable.ic_person)
            return
        }

        try {
            // Gunakan Glide untuk load image dari URI / File / Kamera / Gallery (Termasuk MIUI)
            Glide.with(requireContext())
                .load(Uri.parse(uri))
                .placeholder(R.drawable.ic_person)
                .error(R.drawable.ic_person)
                .into(imgProfile)

        } catch (e: Exception) {
            imgProfile.setImageResource(R.drawable.ic_person)
        }
    }

}
