package com.example.kotlindemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    var number1: EditText? = null
    var number2: EditText? = null
    var Add_button: Button? = null
    var temp: TextView? = null
    var result: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editText = findViewById<EditText>(R.id.edittext)
        val button = findViewById<TextView>(R.id.button)
        val tv_output = findViewById<TextView>(R.id.tv_output)

        button.setOnClickListener {
            val text = editText.text
            tv_output.setText(text)

        }

        number1 = findViewById<View>(R.id.first_number) as EditText
        number2 = findViewById<View>(R.id.second_number) as EditText
        Add_button = findViewById<View>(R.id.button_sum) as Button
        result = findViewById<View>(R.id.result_value) as TextView
        temp = findViewById<View>(R.id.result) as TextView

        Add_button!!.setOnClickListener {

            val num1 = number1!!.text.toString().toDouble()
            val num2 = number2!!.text.toString().toDouble()

            val Addition = num1 + num2

            result!!.text = Addition.toString()
            temp!!.visibility = View.VISIBLE
            result!!.visibility = View.VISIBLE


        }
    }
}