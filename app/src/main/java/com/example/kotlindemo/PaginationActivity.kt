package com.example.kotlindemo

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.kotlindemo.Model.PaginationModel
import com.example.kotlindemo.adapter.PaginationAdapter
import org.json.JSONArray
import org.json.JSONObject

class PaginationActivity : AppCompatActivity() {

    var dataList = mutableListOf<PaginationModel>()
    lateinit var paginationAdapter: PaginationAdapter
    lateinit var recyclerView: RecyclerView
    lateinit var progressBar: ProgressBar
    lateinit var nestedScrollView: NestedScrollView
    lateinit var requestQueue: RequestQueue

    var page: Int = 0
    val limit: Int = 2


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagination)

        requestQueue = Volley.newRequestQueue(this)

        recyclerView = findViewById(R.id.idRVUsers)
        nestedScrollView = findViewById(R.id.nestedscroll)
        progressBar = findViewById(R.id.idPBLoading)

        recyclerView.layoutManager = LinearLayoutManager(this)

        getDataFromAPI(page, limit)

        nestedScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            val childHeight = (v as NestedScrollView).getChildAt(0).measuredHeight
            val scrollViewHeight = v.measuredHeight
            if (scrollY == childHeight - scrollViewHeight) {
                page++
                progressBar.visibility = View.VISIBLE
                getDataFromAPI(page, limit)
            }
        }


    }

    private fun getDataFromAPI(page: Int, limit: Int) {

        if (page > limit) {
            Toast.makeText(this, "That's all the data..", Toast.LENGTH_SHORT).show();
            progressBar.isInvisible
            return
        }

        val url = "https://picsum.photos/v2/list?page=" + page


        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                for (i in 0 until response.length()) {
                    val jsonObject = response.getJSONObject(i)
                    val author: String = jsonObject.getString("author")
                    Log.d("Item", "Item Value: $author")
                    Log.d("TAG", "getDataFromAPI: " + response)
                    val download_url: String = jsonObject.getString("download_url")
                    val urls: String = jsonObject.getString("url")
                    val height: Int = jsonObject.getInt("height")
                    val width: Int = jsonObject.getInt("width")

                    dataList.add(PaginationModel(author, width, height, urls, download_url))

                    paginationAdapter = PaginationAdapter(this, dataList)
                    recyclerView.adapter = paginationAdapter
                }
            },
            Response.ErrorListener { error ->
                // Handle the error
                handleError(error)
            }
        )

        requestQueue.add(jsonArrayRequest)

    }


    private fun handleResponse(response: JSONObject) {

        // Process the response here
        Toast.makeText(this, "Response: $response", Toast.LENGTH_LONG).show()


    }

    private fun handleError(error: VolleyError) {
        // Handle the error here
        Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_LONG).show()
    }

}

