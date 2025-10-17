package com.example.uts_moapps.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.uts_moapps.MyGamesData
import com.example.uts_moapps.R
import com.example.uts_moapps.model.GameModel

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
            game?.let {
                MyGamesData.addGame(it)
                Toast.makeText(
                    requireContext(),
                    "${it.title} added to My Games!",
                    Toast.LENGTH_SHORT
                ).show()
            }
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
