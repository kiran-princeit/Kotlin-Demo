package com.example.kotlindemo.Basic

class KotlinBasic {

    val someInt = 10


    fun sum(a: Int, b: Int, c: Int): Int {
        return a + b + c
    }

    constructor(a: Int = 10, b: Int = 10) {
        val sum = a + b
        println("print some Value 2:" + sum)
    }

    constructor(a: Int = 100, b: Int = 200, c: Int = 300) {
        val sum = a + b + c
        println("print some Value 3:" + sum)
    }
}

fun main() {

    val kb = KotlinBasic(c = 4)
    println("the value is:" + kb.someInt)
    println("the sum is:" + kb.sum(5, 5, 5))
}