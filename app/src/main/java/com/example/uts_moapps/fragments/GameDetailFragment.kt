package com.example.uts_moapps.fragments

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.uts_moapps.MyGamesData
import com.example.uts_moapps.R
import com.example.uts_moapps.model.GameModel
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat


@Suppress("DEPRECATION")
class GameDetailFragment : Fragment() {

    private var game: GameModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            game = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                it.getSerializable("game", GameModel::class.java)
            } else {
                @Suppress("DEPRECATION")
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

        requireActivity().window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.background_dark)

        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)
        btnBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }


        val imgGame = view.findViewById<ImageView>(R.id.imgGameDetail)
        val tvTitle = view.findViewById<TextView>(R.id.tvTitleDetail)
        val tvDeveloper = view.findViewById<TextView>(R.id.tvDeveloper)
        val tvPublisher = view.findViewById<TextView>(R.id.tvPublisher)
        val tvReleaseDate = view.findViewById<TextView>(R.id.tvReleaseDate)
        val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
        val tvGenres = view.findViewById<TextView>(R.id.tvGenres)
        val tvReviews = view.findViewById<TextView>(R.id.tvReviews)
        val btnAdd = view.findViewById<Button>(R.id.btnAddToMyGames)

        game?.apply {
            imgGame.setImageResource(imageResId.takeIf { it != 0 }
                ?: R.drawable.sample_burger_foreground)
            tvTitle.text = title
            tvDeveloper.text = "Developer: $developer"
            tvPublisher.text = "Publisher: $publisher"
            tvReleaseDate.text = "Release Date: $releaseDate"
            tvDescription.text = description
            tvGenres.text = "Genres: ${genres.joinToString(", ")}"
            tvReviews.text = "Reviews: $reviews"
        }

        btnAdd.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.dialog_confirm_purchase, null)
            val dialog = Dialog(requireContext())
            dialog.setContentView(dialogView)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setCancelable(false)

            // 🔹 Tambahkan animasi fade in
            val fadeIn = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_in)
            dialogView.startAnimation(fadeIn)

            val btnYes = dialogView.findViewById<Button>(R.id.btnYes)
            val btnNo = dialogView.findViewById<Button>(R.id.btnNo)
            val tvDialogMessage = dialogView.findViewById<TextView>(R.id.tvDialogMessage)

            btnNo.setOnClickListener {
                // 🔹 Fade out sebelum dismiss
                val fadeOut = AnimationUtils.loadAnimation(requireContext(), R.anim.fade_out)
                dialogView.startAnimation(fadeOut)
                fadeOut.setAnimationListener(object : android.view.animation.Animation.AnimationListener {
                    override fun onAnimationStart(animation: android.view.animation.Animation?) {}
                    override fun onAnimationEnd(animation: android.view.animation.Animation?) {
                        dialog.dismiss()
                    }
                    override fun onAnimationRepeat(animation: android.view.animation.Animation?) {}
                })
            }

            btnYes.setOnClickListener {
                game?.let {
                    MyGamesData.addGame(it)
                }

                // Ubah tampilan ke pesan sukses
                tvDialogMessage.text = "Game Successfully Purchased"
                btnYes.visibility = View.GONE
                btnNo.text = "OK"
            }

            dialog.show()
        }
    }

    companion object {
        fun newInstance(game: GameModel): GameDetailFragment {
            val fragment = GameDetailFragment()
            val bundle = Bundle()
            bundle.putSerializable("game", game)
            fragment.arguments = bundle
            return fragment
        }
    }


}
