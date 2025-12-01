package com.example.scorevision

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.json.JSONArray

@Parcelize // 1. Magic annotation
data class Game(
    val homeTeamName: String,
    val awayTeamName: String,
    val homeScore: String,
    val awayScore: String,
    val gameStatus: String,
    val gameImageUrl: String
) : Parcelable { // 2. Inherit from Parcelable

    companion object {
        fun fromJsonArray(jsonArray: JSONArray): MutableList<Game> {
            val games = mutableListOf<Game>()
            for (i in 0 until jsonArray.length()) {
                val gameJson = jsonArray.getJSONObject(i)
                val home = gameJson.getString("strHomeTeam")
                val away = gameJson.getString("strAwayTeam")
                val homeScore = gameJson.optString("intHomeScore", "-")
                val awayScore = gameJson.optString("intAwayScore", "-")

                val finalHomeScore = if (homeScore == "null") "-" else homeScore
                val finalAwayScore = if (awayScore == "null") "-" else awayScore

                var status = gameJson.optString("strStatus", "")
                if (status.isEmpty() || status == "null") {
                    status = gameJson.optString("dateEvent", "Upcoming")
                }
                val imageUrl = gameJson.optString("strThumb", "")

                games.add(Game(home, away, finalHomeScore, finalAwayScore, status, imageUrl))
            }
            return games
        }
    }
}