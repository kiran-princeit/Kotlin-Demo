package com.example.kotlindemo.Database

import android.content.Context
import android.content.SharedPreferences

object SharedPreferencesUtil {

    private const val PREFS_NAME = "favorites_prefs"
    private const val FAVORITE_KEY_PREFIX = "favorite_"

    fun saveFavoriteStatus(context: Context, itemId: Int, isFavorite: Boolean) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean("$FAVORITE_KEY_PREFIX$itemId", isFavorite).apply()
    }

    fun isFavorite(context: Context, itemId: Int): Boolean {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean("$FAVORITE_KEY_PREFIX$itemId", false)
    }

    fun getAllFavoriteItemIds(context: Context): List<Int> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val allEntries = prefs.all
        val favoriteIds = mutableListOf<Int>()
        for ((key, value) in allEntries) {
            if (key.startsWith(FAVORITE_KEY_PREFIX) && value is Boolean && value) {
                favoriteIds.add(key.removePrefix(FAVORITE_KEY_PREFIX).toInt())
            }
        }
        return favoriteIds
    }

}