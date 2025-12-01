package com.example.scorevision

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDao {

    // Get all favorites (Flow updates the UI automatically when data changes)
    @Query("SELECT * FROM games_table")
    fun getAllFavorites(): Flow<List<Game>>

    // Add a game to favorites
    @Insert
    suspend fun insertGame(game: Game)

    // Remove a game
    @Delete
    suspend fun deleteGame(game: Game)

    // Check if a game is already saved (Optional check)
    @Query("SELECT EXISTS(SELECT * FROM games_table WHERE homeTeamName = :home AND awayTeamName = :away)")
    suspend fun isGameSaved(home: String, away: String): Boolean
}