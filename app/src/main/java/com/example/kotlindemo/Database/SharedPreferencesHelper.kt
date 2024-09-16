package com.example.kotlindemo.Database

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object  SharedPreferencesHelper {

    private const val PREFS_NAME = "com.example.yourapp.PREFS"
    private const val FAVORITE_ITEMS_KEY = "favorite_items"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveFavoriteItem(context: Context, id: Int, isFavorite: Boolean) {
        val prefs = getSharedPreferences(context)
        val gson = Gson()
        val json = prefs.getString(FAVORITE_ITEMS_KEY, null)
        val type = object : TypeToken<MutableMap<Int, Boolean>>() {}.type
        val favoriteItems: MutableMap<Int, Boolean> = gson.fromJson(json, type) ?: mutableMapOf()

        favoriteItems[id] = isFavorite
        val editor = prefs.edit()
        editor.putString(FAVORITE_ITEMS_KEY, gson.toJson(favoriteItems))
        editor.apply()
    }

    fun isFavorite(context: Context, id: Int): Boolean {
        val prefs = getSharedPreferences(context)
        val gson = Gson()
        val json = prefs.getString(FAVORITE_ITEMS_KEY, null)
        val type = object : TypeToken<MutableMap<Int, Boolean>>() {}.type
        val favoriteItems: MutableMap<Int, Boolean> = gson.fromJson(json, type) ?: mutableMapOf()
        return favoriteItems[id] ?: false
    }

    fun loadFavoriteItems(context: Context): Map<Int, Boolean> {
        val prefs = getSharedPreferences(context)
        val gson = Gson()
        val json = prefs.getString(FAVORITE_ITEMS_KEY, null)
        val type = object : TypeToken<MutableMap<Int, Boolean>>() {}.type
        return gson.fromJson(json, type) ?: mutableMapOf()
    }
}