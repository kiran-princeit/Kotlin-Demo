package com.example.kotlindemo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlindemo.Model.UserItem
import com.example.kotlindemo.adapter.RetrofitAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitDataFetchingActivity : AppCompatActivity() {

    var BASE_URL = "https://api.github.com"


    lateinit var rv_retro: RecyclerView
    lateinit var retrofitAdapter: RetrofitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit_data_fetching)

        rv_retro = findViewById(R.id.rv_retro)
        rv_retro.layoutManager = LinearLayoutManager(this)
        rv_retro.adapter
        getAllData()
    }

    private fun getAllData() {
        var retrofit =
            Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build().create(UserInterface::class.java)

        var retroData = retrofit.getData()

        retroData.enqueue(object : Callback<List<UserItem>> {
            override fun onResponse(
                call: Call<List<UserItem>>,
                response: Response<List<UserItem>>
            ) {

                var data = response.body()!!
                Log.e("TINU", data.toString())

                retrofitAdapter = RetrofitAdapter(applicationContext, data)

                rv_retro.adapter = retrofitAdapter
            }

            override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {

                Toast.makeText(
                    getApplicationContext(),
                    "Something WAnt Wrong....",
                    Toast.LENGTH_LONG
                ).show();
            }


        })

    }
}