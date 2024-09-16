package com.example.kotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class ArraysActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arrays)

        val fruits = arrayOf<String>("Apple", "Mango", "Banana", "Orange", "Apple")

        val result = fruits.drop(2) // drops first two elements.
        for( item in result ){
            println( item )
        }

        Log.d("TAG", "onCreate: "+result)
    }


}