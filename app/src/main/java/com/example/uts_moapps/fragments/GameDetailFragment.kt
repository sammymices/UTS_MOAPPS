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
class GameDetailFragment : Fragment() {

    private var game: GameModel? = null

    // callback yang dipanggil setelah user kasih izin notification (Android 13+)
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

        // Set status bar gelap agar menyatu dengan tema
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.background_dark)

        // Bind views
        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)
        val imgGame = view.findViewById<ImageView>(R.id.imgGameDetail)
        val tvTitle = view.findViewById<TextView>(R.id.tvTitleDetail)
        val tvDeveloper = view.findViewById<TextView>(R.id.tvDeveloper)
        val tvPublisher = view.findViewById<TextView>(R.id.tvPublisher)
        val tvReleaseDate = view.findViewById<TextView>(R.id.tvReleaseDate)
        val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
        val tvGenres = view.findViewById<TextView>(R.id.tvGenres)
        val tvReviews = view.findViewById<TextView>(R.id.tvReviews)
        val tvPrice = view.findViewById<TextView>(R.id.tvPrice)
        val btnAdd = view.findViewById<Button>(R.id.btnAddToMyGames)

        // Isi data
        game?.apply {
            imgGame.setImageResource(imageResId)
            tvTitle.text = title
            tvDeveloper.text = "Developer: $developer"
            tvPublisher.text = "Publisher: $publisher"
            tvReleaseDate.text = "Release Date: $releaseDate"
            tvDescription.text = description
            tvGenres.text = "Genres: ${genres.joinToString(", ")}"
            tvReviews.text = "Reviews: $reviews"
            tvPrice.text = price
        }

        btnBack.setOnClickListener { parentFragmentManager.popBackStack() }

        btnAdd.setOnClickListener { showPurchaseDialog() }
    }

    private fun showPurchaseDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_confirm_purchase, null)
        val dialog = Dialog(requireContext())
        dialog.setContentView(dialogView)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)

        val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
        dialogView.startAnimation(fadeIn)

        val btnYes = dialogView.findViewById<Button>(R.id.btnYes)
        val btnNo = dialogView.findViewById<Button>(R.id.btnNo)
        val tvDialogMessage = dialogView.findViewById<TextView>(R.id.tvDialogMessage)
        tvDialogMessage.text = "Do you want to purchase this game?"

        btnYes.setOnClickListener {
            game?.let { g ->
                // Tambah ke MyGames
                MyGamesData.addGame(g)

                // Buat blok untuk kirim in-app + push notif
                val pushBlock = {
                    // In-app + push (overload NotificationCenter.add(context, ...))
                    NotificationCenter.add(
                        context = requireContext(),
                        sender = "Store",
                        message = "Purchased \"${g.title}\" — added to My Games.",
                        iconRes = R.drawable.ic_profile
                    )
                }

                // Cek izin notifikasi untuk Android 13+
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

            tvDialogMessage.text = "Game Successfully Purchased!"
            btnYes.visibility = View.GONE
            btnNo.text = "OK"
        }

        btnNo.setOnClickListener { dialog.dismiss() }

        dialog.show()
    }

    companion object {
        fun newInstance(game: GameModel): GameDetailFragment {
            val fragment = GameDetailFragment()
            val bundle = Bundle().apply { putSerializable("game", game) }
            fragment.arguments = bundle
            return fragment
            }
        }
}