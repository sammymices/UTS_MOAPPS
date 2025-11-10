package com.example.uts_moapps

import android.content.Context

class UserPreferences(context: Context) {

    private val prefs = context.getSharedPreferences("user_profile", Context.MODE_PRIVATE)

    fun saveUsername(name: String) {
        prefs.edit().putString("username", name).apply()
    }

    fun getUsername(): String = prefs.getString("username", "User") ?: "User"

    fun saveProfileImage(uri: String) {
        prefs.edit().putString("profile_image", uri).apply()
    }

    fun getProfileImage(): String? = prefs.getString("profile_image", null)
}
