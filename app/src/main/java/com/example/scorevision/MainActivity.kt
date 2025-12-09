package com.example.scorevision

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {

    private lateinit var rvGames: RecyclerView
    private lateinit var adapter: GamesAdapter
    private lateinit var swipeContainer: SwipeRefreshLayout
    private var gamesList = mutableListOf<Game>()

    // Define the 3 Endpoints (ESPN API)
    private val NBA_URL = "https://site.api.espn.com/apis/site/v2/sports/basketball/nba/scoreboard"
    private val NFL_URL = "https://site.api.espn.com/apis/site/v2/sports/football/nfl/scoreboard"
    private val MLB_URL = "https://site.api.espn.com/apis/site/v2/sports/baseball/mlb/scoreboard"

    // Default to NBA
    private var currentUrl = NBA_URL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Find Views
        rvGames = findViewById(R.id.rvGames)
        swipeContainer = findViewById(R.id.swipeContainer)
        val btnNBA = findViewById<Button>(R.id.btnNBA)
        val btnNFL = findViewById<Button>(R.id.btnNFL)
        val btnMLB = findViewById<Button>(R.id.btnMLB)

        // Setup Adapter
        adapter = GamesAdapter(gamesList)
        rvGames.adapter = adapter
        rvGames.layoutManager = LinearLayoutManager(this)

        // --- BUTTON LISTENERS ---
        btnNBA.setOnClickListener {
            currentUrl = NBA_URL
            fetchGames()
        }
        btnNFL.setOnClickListener {
            currentUrl = NFL_URL
            fetchGames()
        }
        btnMLB.setOnClickListener {
            currentUrl = MLB_URL
            fetchGames()
        }

        // --- REFRESH LISTENER (The "Pull" Action) ---
        swipeContainer.setOnRefreshListener {
            Log.d("ScoreVision", "Refreshing...")
            fetchGames()
        }

        // Optional: Make the spinner colorful
        swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )

        // Initial Load
        fetchGames()
    }

    private fun fetchGames() {
        // Show spinner if it's not already showing (e.g. on button click)
        swipeContainer.isRefreshing = true

        val client = AsyncHttpClient()
        client.get(currentUrl, object : JsonHttpResponseHandler() {

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                try {
                    val eventsArray = json.jsonObject.getJSONArray("events")
                    val newGames = Game.fromJsonArray(eventsArray)

                    gamesList.clear()
                    gamesList.addAll(newGames)
                    adapter.notifyDataSetChanged()

                    // STOP the spinner
                    swipeContainer.isRefreshing = false

                } catch (e: Exception) {
                    Log.e("ScoreVision", "Error: $e")
                    swipeContainer.isRefreshing = false
                }
            }

            override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?) {
                Log.e("ScoreVision", "API Failed")
                swipeContainer.isRefreshing = false
            }
        })
    }
}