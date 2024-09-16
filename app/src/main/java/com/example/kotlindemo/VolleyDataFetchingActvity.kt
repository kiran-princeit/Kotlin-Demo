package com.example.kotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.kotlindemo.adapter.VolleyDataAdapter
import com.google.gson.JsonArray
import org.json.JSONArray
import org.json.JSONException

class VolleyDataFetchingActvity : AppCompatActivity() {

    private lateinit var requestQueue: RequestQueue
    private lateinit var rv_volley: RecyclerView
    private val movieList = mutableListOf<Movie>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volley_data_fetching_actvity)


        rv_volley = findViewById(R.id.rv_volley)
        rv_volley.layoutManager = LinearLayoutManager(this)
        val adapter = VolleyDataAdapter(this, movieList)
        rv_volley.adapter = adapter

        // Initialize RequestQueue
        requestQueue = Volley.newRequestQueue(this)

        // Fetch movies
        fetchMovies(adapter)
    }

    private fun fetchMovies(adapter: VolleyDataAdapter) {
        val url = "https://www.json-generator.com/api/json/get/cfsXpFGwwO?indent=2"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONArray> { response ->
                movieList.clear()
                for (i in 0 until response.length()) {
                    try {
                        val jsonObject = response.getJSONObject(i)
                        val title = jsonObject.getString("title")
                        val overview = jsonObject.getString("overview")
                        val poster = jsonObject.getString("poster")
                        val rating = jsonObject.getDouble("rating")

                        val movie = Movie(title, poster, overview, rating)
                        movieList.add(movie)

                        Log.e("TAG", "fetchMovies: " + movieList)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
                adapter.notifyDataSetChanged()
            },
            Response.ErrorListener { error: VolleyError ->
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            }
        )

        requestQueue.add(jsonArrayRequest)
    }
}

