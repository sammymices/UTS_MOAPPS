package com.example.uts_moapps.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.uts_moapps.R
import com.example.uts_moapps.model.NewsData

class NewsDetailFragment : Fragment() {

    // daftar berita (harus sama urutan & isi seperti di NewsFragment)
    private val newsList = listOf(
        NewsData("Battle Pass Season 21: “Brave Archer”", "New rewards and challenges await!", R.drawable.sample1),
        NewsData("Screenshot Competition – High Horizons!", "Show your best screenshots!", R.drawable.sample2),
        NewsData("New Vehicle Line Announced", "Developers reveal their next major update.", R.drawable.sample3),
        NewsData("New Vehicle Line Announced", "Developers reveal their next major update.", R.drawable.sample4),
        NewsData("Map Expansion Incoming!", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", R.drawable.sample5),
        NewsData("Weekend Bonus Event", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", R.drawable.sample6),
        NewsData("Community Skin Contest", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", R.drawable.sample7),
        NewsData("Server Maintenance Notice", "Lorem ipsum dolor sit amet, consectetur adipiscing elit.", R.drawable.sample8)
    )

    private var currentIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_news_detail, container, false)

        val title = view.findViewById<TextView>(R.id.tvDetailTitle)
        val description = view.findViewById<TextView>(R.id.tvDetailDescription)
        val image = view.findViewById<ImageView>(R.id.ivDetailImage)

        // tombol back / close (tetap seperti sebelumnya)
        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)
        val btnClose = view.findViewById<ImageButton>(R.id.btnClose)

        btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }
        btnClose.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        // prev / next buttons (di layout mereka bernama btnScrollUp / btnScrollDown)
        val btnPrev = view.findViewById<ImageButton>(R.id.btnScrollUp)
        val btnNext = view.findViewById<ImageButton>(R.id.btnScrollDown)

        // ambil argumen (harus ada karena NewsAdapter mengirimkan)
        val args = requireArguments()
        val argTitle = args.getString("title") ?: ""
        val argImage = args.getInt("imageRes")

        // cari indeks berita saat ini berdasarkan title + imageRes (lebih aman)
        currentIndex = newsList.indexOfFirst { it.title == argTitle && it.imageRes == argImage }
        if (currentIndex == -1) {
            // kalau tidak ketemu, fallback ke 0
            currentIndex = 0
        }

        // fungsi util untuk menampilkan berita sesuai currentIndex
        fun showCurrent() {
            val data = newsList[currentIndex]
            title.text = data.title
            description.text = data.description
            image.setImageResource(data.imageRes)

            // aktif / nonaktifkan tombol prev/next bila di ujung
            if (currentIndex <= 0) {
                btnPrev.isEnabled = false
                btnPrev.alpha = 0.5f
            } else {
                btnPrev.isEnabled = true
                btnPrev.alpha = 1.0f
            }

            if (currentIndex >= newsList.size - 1) {
                btnNext.isEnabled = false
                btnNext.alpha = 0.5f
            } else {
                btnNext.isEnabled = true
                btnNext.alpha = 1.0f
            }
        }

        // attach listener prev / next
        btnPrev.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex -= 1
                showCurrent()
                // scroll to top of content agar user langsung lihat judul
                val scrollView = view.findViewById<ScrollView>(R.id.scrollViewDetail)
                scrollView?.post { scrollView.fullScroll(ScrollView.FOCUS_UP) }
            }
        }

        btnNext.setOnClickListener {
            if (currentIndex < newsList.size - 1) {
                currentIndex += 1
                showCurrent()
                val scrollView = view.findViewById<ScrollView>(R.id.scrollViewDetail)
                scrollView?.post { scrollView.fullScroll(ScrollView.FOCUS_UP) }
            }
        }

        // tampilkan awal
        showCurrent()

        return view
    }
}
