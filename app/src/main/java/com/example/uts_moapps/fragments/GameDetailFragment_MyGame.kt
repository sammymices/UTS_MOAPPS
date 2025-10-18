package com.example.uts_moapps.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.uts_moapps.MyGamesData
import com.example.uts_moapps.R
import com.example.uts_moapps.model.GameModel

@Suppress("DEPRECATION")
class GameDetailFragmentMyGame : Fragment() {

    private var game: GameModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            game = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                it.getSerializable("game", GameModel::class.java)
            } else {
                it.getSerializable("game") as? GameModel
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_game_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.background_dark)

        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)
        val btnRefund = view.findViewById<Button>(R.id.btnAddToMyGames)
        val imgGame = view.findViewById<ImageView>(R.id.imgGameDetail)
        val tvTitle = view.findViewById<TextView>(R.id.tvTitleDetail)
        val tvDeveloper = view.findViewById<TextView>(R.id.tvDeveloper)
        val tvPublisher = view.findViewById<TextView>(R.id.tvPublisher)
        val tvReleaseDate = view.findViewById<TextView>(R.id.tvReleaseDate)
        val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
        val tvGenres = view.findViewById<TextView>(R.id.tvGenres)
        val tvReviews = view.findViewById<TextView>(R.id.tvReviews)
        val tvPrice = view.findViewById<TextView>(R.id.tvPrice)

        // 🔹 Hide price (karena sudah dimiliki)
        tvPrice.visibility = View.GONE

        // 🔹 Ganti tombol jadi Refund
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

        btnRefund.setOnClickListener {
            showRefundDialog()
        }
    }

    private fun showRefundDialog() {
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
        tvDialogMessage.text = "Are you sure you want to refund this game?"

        btnYes.setOnClickListener {
            game?.let { MyGamesData.removeGame(it) }
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
            val fragment = GameDetailFragmentMyGame()
            val args = Bundle()
            args.putSerializable("game", game)
            fragment.arguments = args
            return fragment
        }
    }
}
