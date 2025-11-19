package com.example.scorevision

import org.json.JSONArray

data class Game(
    val homeTeamName: String,
    val awayTeamName: String,
    val homeScore: String,
    val awayScore: String,
    val gameStatus: String,
    val gameImageUrl: String
) {
    companion object {
        fun fromJsonArray(jsonArray: JSONArray): MutableList<Game> {
            val games = mutableListOf<Game>()

            for (i in 0 until jsonArray.length()) {
                val gameJson = jsonArray.getJSONObject(i)

                val home = gameJson.getString("strHomeTeam")
                val away = gameJson.getString("strAwayTeam")

                // Handle potential null scores safely
                val homeScore = gameJson.optString("intHomeScore", "-")
                val awayScore = gameJson.optString("intAwayScore", "-")

                // FORMATTING FIX:
                // If the score is null (future game), show "vs" or empty
                val finalHomeScore = if (homeScore == "null") "-" else homeScore
                val finalAwayScore = if (awayScore == "null") "-" else awayScore

                // Try to get the date, otherwise fallback to status
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