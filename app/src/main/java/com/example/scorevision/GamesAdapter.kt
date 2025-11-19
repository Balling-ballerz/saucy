package com.example.scorevision

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GamesAdapter(private val games: List<Game>) : RecyclerView.Adapter<GamesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = games[position]
        holder.bind(game)
    }

    override fun getItemCount() = games.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvHomeTeam: TextView = itemView.findViewById(R.id.tvHomeTeam)
        private val tvAwayTeam: TextView = itemView.findViewById(R.id.tvAwayTeam)
        private val tvHomeScore: TextView = itemView.findViewById(R.id.tvHomeScore)
        private val tvAwayScore: TextView = itemView.findViewById(R.id.tvAwayScore)
        private val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)

        // NEW: The single big image
        private val ivGameImage: ImageView = itemView.findViewById(R.id.ivGameImage)

        fun bind(game: Game) {
            tvHomeTeam.text = game.homeTeamName
            tvAwayTeam.text = game.awayTeamName
            tvHomeScore.text = game.homeScore
            tvAwayScore.text = game.awayScore
            tvStatus.text = game.gameStatus

            // Load the Game Banner Image
            Glide.with(itemView.context)
                .load(game.gameImageUrl)
                .placeholder(R.mipmap.ic_launcher) // Show this while loading
                .into(ivGameImage)
        }
    }
}