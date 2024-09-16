package com.example.kotlindemo

import com.example.kotlindemo.Model.UserItem
import retrofit2.Call
import retrofit2.http.GET

interface UserInterface {
    @GET("/users")
    fun getData():Call<List<UserItem>>
}