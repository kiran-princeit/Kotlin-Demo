package com.example.kotlindemo.Pagination

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyLog
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.kotlindemo.MainActivity
import com.example.kotlindemo.Pagination.adapter.SchemeAdapter
import com.example.kotlindemo.Pagination.model.Scheme
import com.example.kotlindemo.R
import org.json.JSONObject

class PaginationVolleyActivity : AppCompatActivity() {
    lateinit var schemeAdapter: SchemeAdapter
    lateinit var recyclerView: RecyclerView
    private var scheme = mutableListOf<Scheme>()
    private var currentPage = 1
    private var lastPage = 1
    private var isLoading = false

    val TAG = "MainActivity"

    lateinit var requestQueue: RequestQueue

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagination_volley)

        recyclerView = findViewById(R.id.rv_volley_pagination)

        requestQueue = Volley.newRequestQueue(this)

        schemeAdapter = SchemeAdapter(scheme)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = schemeAdapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val visiblItemCount = layoutManager.childCount
                val totelItemCount = layoutManager.itemCount
                val pastVisibleItem = layoutManager.findFirstVisibleItemPosition()

                if (isLoading && (visiblItemCount + pastVisibleItem) >= totelItemCount) {
                    laodMoreData()
                }
            }
        })
        fetchData("http://localhost:8081/api/v1/search?page=$currentPage")

    }

    private fun laodMoreData() {
        if (currentPage > lastPage) {
            currentPage++
            fetchData("http://localhost:8081/api/v1/search?page=$currentPage")
        }
    }

    private fun fetchData(s: String) {

        isLoading = true
        val jsonObjectRequest =
            JsonObjectRequest(Request.Method.GET, s, null, Response.Listener { response ->

                parseResponse(response)

                Log.d(TAG, "fetchData: " + response)
                isLoading = false


            }, Response.ErrorListener { error ->
                VolleyLog.d(TAG, "Error :" + error.message)
                isLoading = false
            })
        requestQueue.add(jsonObjectRequest)
    }

    private fun parseResponse(response: JSONObject?) {

        val data = response?.getJSONArray("data")

        Log.d(TAG, "parseResponse: " + response)
        val newScheme = mutableListOf<Scheme>()
        if (data != null) {
            for (i in 0 until data.length()) {
                val item = data.getJSONObject(i)
                newScheme.add(parseScheme(item))
            }
            scheme.addAll(newScheme)
            schemeAdapter.notifyDataSetChanged()

            currentPage = response.getInt("current_page")
            lastPage = response.getInt("last_page")

            if (currentPage < lastPage) {
                val nextpageUrl = response.getString("next_page_url")
                fetchData(nextpageUrl)
            }


        }
    }

    private fun parseScheme(item: JSONObject?): Scheme {
//        if (item != null) {
//      {
        return Scheme(
            id = item!!.getInt("id"),
            source = item.getString("source"),
            name = item.getString("name"),
            sector = item.getString("sector"),
            government = item.getString("government"),
            eligible_beneficiaries = item.getString("eligible_beneficiaries"),
            requirements = item.getString("requirements"),
            benefits = item.getString("benefits"),
            how_to_apply = item.getString("how_to_apply"),
            profession = item.optString("profession", ""),
            nationality = item.optString("nationality", ""),
            gender = item.getString("gender"),
            social_category = item.getJSONArray("social_category").let { jsonArray ->
                List(jsonArray.length()) { index ->
                    jsonArray.getString(index)
                }
            },
            bpl = item.optString("bpl", ""),
            maximum_income = item.optString("maximum_income", ""),
            maximum_monthly_income = item.getString("maximum_monthly_income"),
            min_age = item.getInt("min_age"),
            max_age = item.getInt("max_age"),
            age_relaxation = item.optString("age_relaxation", ""),
            qualification = item.getInt("qualification"),
            employed = item.optString("employed", ""),
            domicile = item.optString("domicile", ""),
            marital_status = item.getString("marital_status"),
            parents_profession = item.optString("parents_profession", ""),
            person_with_disabilities = item.optString("person_with_disabilities", ""),
            current_student = item.getString("current_student"),
            min_marks_in_previous_examination = item.optString(
                "min_marks_in_previous_examination",
                ""
            ),
            religion = item.optString("religion", ""),
            isDeleted = item.getBoolean("isDeleted"),
            isLatest = item.getBoolean("isLatest"),
            isPopular = item.getBoolean("isPopular"),
            isHtml = item.getBoolean("isHtml"),
            state_url = item.getString("state_url"),
            sector_url = item.getString("sector_url")
        )
    }
}