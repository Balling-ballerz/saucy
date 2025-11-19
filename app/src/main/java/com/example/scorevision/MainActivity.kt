package com.example.scorevision

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {

    private lateinit var rvGames: RecyclerView
    private lateinit var adapter: GamesAdapter
    private var gamesList = mutableListOf<Game>()

    // URL for the API (Using the free '3' key for NBA past events)
    private val API_URL = "https://www.thesportsdb.com/api/v1/json/3/eventspastleague.php?id=4387"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Find the RecyclerView
        rvGames = findViewById(R.id.rvGames)

        // 2. Create the Adapter with an empty list initially
        adapter = GamesAdapter(gamesList)

        // 3. Attach the Adapter to the RecyclerView
        rvGames.adapter = adapter
        rvGames.layoutManager = LinearLayoutManager(this)

        // 4. Fetch the REAL data from the internet
        fetchGames()
    }

    private fun fetchGames() {
        val client = AsyncHttpClient()
        // Make a GET request to the API URL
        client.get(API_URL, object : JsonHttpResponseHandler() {

            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.d("ScoreVision", "API Success: $json")

                try {
                    // 1. Get the "events" array from the JSON response
                    val eventsArray = json.jsonObject.getJSONArray("events")

                    // 2. Convert that JSON into a list of Game objects
                    val newGames = Game.fromJsonArray(eventsArray)

                    // 3. Clear the old list and add the new games
                    gamesList.clear()
                    gamesList.addAll(newGames)

                    // 4. Tell the adapter the data changed so it updates the screen
                    adapter.notifyDataSetChanged()

                } catch (e: Exception) {
                    Log.e("ScoreVision", "Error parsing JSON: $e")
                }
            }

            override fun onFailure(statusCode: Int, headers: Headers?, response: String?, throwable: Throwable?) {
                Log.e("ScoreVision", "API Failed. Status: $statusCode")
            }
        })
    }
}