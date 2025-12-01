package com.example.scorevision

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        // 1. Get the Game object sent from the MainActivity
        // We use "getParcelableExtra" because we made Game parcelable
        val game = intent.getParcelableExtra<Game>("GAME_EXTRA")

        // 2. Find views
        val ivImage = findViewById<ImageView>(R.id.ivDetailImage)
        val tvTitle = findViewById<TextView>(R.id.tvDetailTitle)
        val tvHomeScore = findViewById<TextView>(R.id.tvDetailHomeScore)
        val tvAwayScore = findViewById<TextView>(R.id.tvDetailAwayScore)
        val tvStatus = findViewById<TextView>(R.id.tvDetailStatus)

        // 3. Populate views if game is not null
        if (game != null) {
            tvTitle.text = "${game.homeTeamName} vs ${game.awayTeamName}"
            tvHomeScore.text = game.homeScore
            tvAwayScore.text = game.awayScore
            tvStatus.text = game.gameStatus

            Glide.with(this)
                .load(game.gameImageUrl)
                .placeholder(R.mipmap.ic_launcher)
                .into(ivImage)
        }
    }
}