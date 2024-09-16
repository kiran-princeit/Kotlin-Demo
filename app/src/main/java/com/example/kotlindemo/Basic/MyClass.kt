package com.example.kotlindemo.Basic

import android.util.Log

class MyClass {

    companion object {

        @JvmStatic
        fun main(arg: Array<String>) {
            var a = "Apple"
            a = "banana"
            println("Hello World...!$a")

            println(" ${add(10, 5)}")
            println(" ${add(10, 5, 5)}")

            var num = 10
            val msg = if (num < 100) "No,is Greater..!" else "No,is Smaller..!"
            println(msg)


            var no = 10

            when (no) {
                1 -> {
                    println("No")
                }

                100 -> {
                    println("yes ")
                }
            }


            var number = 0
            for (i in 0 until 10) {
                println(number++)
            }

            var digit = 90

            while (digit < 100) {
                println("number is:$digit")
                digit++
            }


            var digits = 10
            do {
                println("number$digits")
            } while (digits > 10)


            val (x, y) = Pair("Tinu", 9)
            println("$x, $y")

            val name = Pair("Manya", "Daivik")
            println("${name.first},${name.second}")

            val nam = Pair("Manya", Pair("Neeva", Pair("Daivik", "Ram")))

            println("${nam.first},${nam.second.second}")


            val (m, n, o) = Triple("Hello", "Good Morning", 1)
            println("$m,$n,$o")

            val value = Triple("A", "B", Triple("C", "True", Triple("7", "6,", "9")))
            println("${value.third}")
        }

        fun add(a: Int, b: Int): Any {
            return a + b
        }

        fun add(a: Int, b: Int, c: Int): Any {
            return a + b + c
        }


    }

}