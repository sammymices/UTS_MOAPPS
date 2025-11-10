package com.example.uts_moapps.model

import android.Manifest
import android.content.Context
import androidx.annotation.RequiresPermission
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.uts_moapps.R
import com.example.uts_moapps.notify.Notifier

object NotificationCenter {
    private val _notifications = MutableLiveData<MutableList<NotificationData>>(mutableListOf())
    val notifications: LiveData<MutableList<NotificationData>> = _notifications

    /** Tambah hanya ke in-app list (tidak push). */
    fun add(sender: String, message: String, iconRes: Int = R.drawable.ic_profile) {
        val list = _notifications.value ?: mutableListOf()
        list.add(0, NotificationData(sender, message, iconRes))
        _notifications.postValue(list)
    }

    /** Tambah ke in-app list + PUSH ke Android notification. */
    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
    fun add(
        context: Context,
        sender: String,
        message: String,
        iconRes: Int = R.drawable.ic_profile
    ) {
        add(sender, message, iconRes) // tetap masukkan ke LiveData
        Notifier.notify(
            context = context,
            title = sender,
            message = message,
            smallIconRes = iconRes
            )
    }
}