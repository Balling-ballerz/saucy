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

        val game = intent.getParcelableExtra<Game>("GAME_EXTRA")

        if (game != null) {
            findViewById<TextView>(R.id.tvDetailHomeScore).text = game.homeScore
            findViewById<TextView>(R.id.tvDetailAwayScore).text = game.awayScore
            findViewById<TextView>(R.id.tvDetailStatus).text = game.gameStatus

            findViewById<TextView>(R.id.tvHomeRecord).text = game.homeRecord
            findViewById<TextView>(R.id.tvAwayRecord).text = game.awayRecord
            findViewById<TextView>(R.id.tvVenue).text = game.venue

            val ivHome = findViewById<ImageView>(R.id.ivDetailHomeLogo)
            val ivAway = findViewById<ImageView>(R.id.ivDetailAwayLogo)

            // 1. CATCH THE ANIMATION
            // We set the static names we used in the Pair.create() in Adapter
            ivHome.transitionName = "homeLogoTransition"
            ivAway.transitionName = "awayLogoTransition"

            Glide.with(this).load(game.homeLogoUrl).into(ivHome)
            Glide.with(this).load(game.awayLogoUrl).into(ivAway)
        }
    }
}