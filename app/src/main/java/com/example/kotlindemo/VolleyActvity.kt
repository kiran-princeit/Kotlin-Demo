package com.example.kotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.kotlindemo.Model.VolleyData
import com.example.kotlindemo.adapter.AdapterDataVolley
import com.google.gson.JsonObject
import org.json.JSONArray
import org.json.JSONObject

class VolleyActvity : AppCompatActivity() {

    val str_url = "https://reqres.in/api/users?page=1"

    lateinit var requestQueue: RequestQueue

    lateinit var recyclerView: RecyclerView

    val dataList = mutableListOf<VolleyData>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_volley_actvity)

        recyclerView = findViewById(R.id.rv_data_volley)
        requestQueue = Volley.newRequestQueue(this)

        recyclerView.layoutManager = LinearLayoutManager(this)

        getVolleyData()

    }

    private fun getVolleyData() {

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            str_url,
            null,
            { response ->

                val jsonArray: JSONArray = response.getJSONArray("data")

                for (i in 0 until jsonArray.length()) {

                    val jsonObject: JSONObject = jsonArray.getJSONObject(i)

                    val id: Int = jsonObject.getInt("id")
                    val email: String = jsonObject.getString("email")
                    val firstname: String = jsonObject.getString("first_name")
                    val lastname: String = jsonObject.getString("last_name")
                    val avtar: String = jsonObject.getString("avatar")

                    dataList.add(VolleyData(id, avtar, email, firstname, lastname))

                    val madapter = AdapterDataVolley(this, dataList)
                    recyclerView.adapter = madapter
                }


            },
            { error ->
                // Handle error
                error.printStackTrace()
            }
        )

        // Add the request to the RequestQueue.
        requestQueue.add(jsonObjectRequest)

    }
}



