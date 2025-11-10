package com.example.uts_moapps.fragments

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.uts_moapps.MyGamesData
import com.example.uts_moapps.R
import com.example.uts_moapps.model.GameModel
import com.example.uts_moapps.model.NotificationCenter

@Suppress("DEPRECATION")
class GameDetailFragmentMyGame : Fragment() {

    private var game: GameModel? = null

    // callback setelah user memberikan izin notifikasi (Android 13+)
    private var onNotifPermissionGranted: (() -> Unit)? = null
    private val requestNotifPermission = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        onNotifPermissionGranted?.let { cb ->
            if (granted) cb()
            onNotifPermissionGranted = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            game = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getSerializable("game", GameModel::class.java)
            } else {
                it.getSerializable("game") as? GameModel
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_game_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.background_dark)

        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)
        val btnRefund = view.findViewById<Button>(R.id.btnAddToMyGames) // reuse id tombol
        val imgGame = view.findViewById<ImageView>(R.id.imgGameDetail)
        val tvTitle = view.findViewById<TextView>(R.id.tvTitleDetail)
        val tvDeveloper = view.findViewById<TextView>(R.id.tvDeveloper)
        val tvPublisher = view.findViewById<TextView>(R.id.tvPublisher)
        val tvReleaseDate = view.findViewById<TextView>(R.id.tvReleaseDate)
        val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
        val tvGenres = view.findViewById<TextView>(R.id.tvGenres)
        val tvReviews = view.findViewById<TextView>(R.id.tvReviews)
        val tvPrice = view.findViewById<TextView>(R.id.tvPrice)

        // Hide price karena sudah dimiliki
        tvPrice.visibility = View.GONE

        // Ubah tombol jadi REFUND
        btnRefund.text = "REFUND GAME"
        btnRefund.backgroundTintList =
            ContextCompat.getColorStateList(requireContext(), R.color.error_red)

        game?.apply {
            imgGame.setImageResource(imageResId)
            tvTitle.text = title
            tvDeveloper.text = "Developer: $developer"
            tvPublisher.text = "Publisher: $publisher"
            tvReleaseDate.text = "Release Date: $releaseDate"
            tvDescription.text = description
            tvGenres.text = "Genres: ${genres.joinToString(", ")}"
            tvReviews.text = "Reviews: $reviews"
        }

        btnBack.setOnClickListener { parentFragmentManager.popBackStack() }
        btnRefund.setOnClickListener { showRefundDialog() }
    }

    private fun showRefundDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_confirm_purchase, null)
        val dialog = Dialog(requireContext()).apply {
            setContentView(dialogView)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            setCancelable(false)
        }

        val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        dialogView.startAnimation(fadeIn)

        val btnYes = dialogView.findViewById<Button>(R.id.btnYes)
        val btnNo = dialogView.findViewById<Button>(R.id.btnNo)
        val tvDialogMessage = dialogView.findViewById<TextView>(R.id.tvDialogMessage)
        tvDialogMessage.text = "Are you sure you want to refund this game?"

        btnYes.setOnClickListener {
            game?.let { g ->
                // 1) Hapus dari koleksi
                MyGamesData.removeGame(g)

                // 2) Siapkan blok kirim notif (in-app + push)
                val pushBlock = {
                    NotificationCenter.add(
                        context = requireContext(),            // in-app + push
                        sender = "Store",
                        message = "Refunded \"${g.title}\" — removed from My Games.",
                        iconRes = R.drawable.ic_profile
                    )
                }

                // 3) Cek & minta permission untuk Android 13+
                val needPermission = Build.VERSION.SDK_INT >= 33 &&
                        ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED

                if (needPermission) {
                    onNotifPermissionGranted = pushBlock
                    requestNotifPermission.launch(Manifest.permission.POST_NOTIFICATIONS)
                } else {
                    pushBlock()
                }
            }

            tvDialogMessage.text = "Game refunded successfully!"
            btnYes.visibility = View.GONE
            btnNo.text = "OK"

            btnNo.setOnClickListener {
                dialog.dismiss()
                parentFragmentManager.popBackStack() // kembali ke MyGames
            }
        }

        btnNo.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

    companion object {
        fun newInstance(game: GameModel): GameDetailFragmentMyGame {
            return GameDetailFragmentMyGame().apply {
                arguments = Bundle().apply { putSerializable("game", game) }
            }
            }
        }
}