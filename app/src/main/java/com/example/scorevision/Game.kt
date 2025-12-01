package com.example.scorevision

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import org.json.JSONArray

@Parcelize
@Entity(tableName = "games_table") // <--- THIS LINE IS MANDATORY FOR ROOM
data class Game(
    val homeTeamName: String,
    val awayTeamName: String,
    val homeScore: String,
    val awayScore: String,
    val gameStatus: String,
    val homeLogoUrl: String,
    val awayLogoUrl: String,
    val homeRecord: String,
    val awayRecord: String,
    val venue: String,

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0
) : Parcelable {

    companion object {
        fun fromJsonArray(jsonArray: JSONArray): MutableList<Game> {
            val games = mutableListOf<Game>()

            for (i in 0 until jsonArray.length()) {
                val eventJson = jsonArray.getJSONObject(i)
                val competitions = eventJson.getJSONArray("competitions")
                val competition = competitions.getJSONObject(0)
                val competitors = competition.getJSONArray("competitors")

                val venueJson = competition.optJSONObject("venue")
                val venue = venueJson?.optString("fullName") ?: "Unknown Venue"

                val statusObj = eventJson.getJSONObject("status")
                val typeObj = statusObj.getJSONObject("type")
                val gameStatus = typeObj.getString("shortDetail")

                val teamA = competitors.getJSONObject(0)
                val teamB = competitors.getJSONObject(1)

                val homeTeamJson = if (teamA.getString("homeAway") == "home") teamA else teamB
                val awayTeamJson = if (teamA.getString("homeAway") == "home") teamB else teamA

                val homeTeamName = homeTeamJson.getJSONObject("team").getString("shortDisplayName")
                val awayTeamName = awayTeamJson.getJSONObject("team").getString("shortDisplayName")
                val homeLogo = homeTeamJson.getJSONObject("team").optString("logo", "")
                val awayLogo = awayTeamJson.getJSONObject("team").optString("logo", "")
                val homeScore = homeTeamJson.optString("score", "0")
                val awayScore = awayTeamJson.optString("score", "0")

                var homeRecord = ""
                val homeRecordsJson = homeTeamJson.optJSONArray("records")
                if (homeRecordsJson != null && homeRecordsJson.length() > 0) {
                    homeRecord = homeRecordsJson.getJSONObject(0).optString("summary", "")
                }

                var awayRecord = ""
                val awayRecordsJson = awayTeamJson.optJSONArray("records")
                if (awayRecordsJson != null && awayRecordsJson.length() > 0) {
                    awayRecord = awayRecordsJson.getJSONObject(0).optString("summary", "")
                }

                games.add(Game(homeTeamName, awayTeamName, homeScore, awayScore, gameStatus, homeLogo, awayLogo, homeRecord, awayRecord, venue))
            }
            return games
        }
    }
}