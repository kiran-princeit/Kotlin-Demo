package com.example.kotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView

class ArraysActivity : AppCompatActivity() {

    lateinit var tv_array: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_arrays)
        tv_array=findViewById(R.id.tv_array)


        val fruits = arrayOf<String>("Apple", "Mango", "Banana", "Orange", "Apple")

        val result = fruits.drop(2) // drops first two elements.
        for( item in result ){
            println( item )
        }
        tv_array.setText(result.toString())
        Log.d("TAG", "onCreate: "+result)
    }


}