package com.example.scorevision

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GamesAdapter(private val games: List<Game>) : RecyclerView.Adapter<GamesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(games[position])
    }

    override fun getItemCount() = games.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvHomeTeam: TextView = itemView.findViewById(R.id.tvHomeTeam)
        private val tvAwayTeam: TextView = itemView.findViewById(R.id.tvAwayTeam)
        private val tvHomeScore: TextView = itemView.findViewById(R.id.tvHomeScore)
        private val tvAwayScore: TextView = itemView.findViewById(R.id.tvAwayScore)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        private val ivHomeLogo: ImageView = itemView.findViewById(R.id.ivHomeLogo)
        private val ivAwayLogo: ImageView = itemView.findViewById(R.id.ivAwayLogo)

        fun bind(game: Game) {
            tvHomeTeam.text = game.homeTeamName
            tvAwayTeam.text = game.awayTeamName
            tvHomeScore.text = game.homeScore
            tvAwayScore.text = game.awayScore
            tvStatus.text = game.gameStatus

            Glide.with(itemView.context).load(game.homeLogoUrl).into(ivHomeLogo)
            Glide.with(itemView.context).load(game.awayLogoUrl).into(ivAwayLogo)

            // 1. ASSIGN UNIQUE NAMES for the animation
            // This ensures "Lakers" logo knows to fly to the "Lakers" spot
            ivHomeLogo.transitionName = "home_${game.homeTeamName}"
            ivAwayLogo.transitionName = "away_${game.awayTeamName}"

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("GAME_EXTRA", game)

                // 2. CREATE THE ANIMATION BUNDLE
                // We tell Android: "Move ivHomeLogo to the next screen"
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    itemView.context as Activity,
                    Pair.create(ivHomeLogo, "homeLogoTransition"),
                    Pair.create(ivAwayLogo, "awayLogoTransition")
                )

                // 3. START WITH ANIMATION
                itemView.context.startActivity(intent, options.toBundle())
            }
        }
    }
}