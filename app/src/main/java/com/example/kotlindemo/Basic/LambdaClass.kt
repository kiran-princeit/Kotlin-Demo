package com.example.kotlindemo.Basic

fun Square(a: Int): Int {
    return a * a

}

fun sum(a: Int, b: Int): Int {
    return a + b

}

val adds = { x: Int, y: Int -> x + y }
val add: (Int, Int) -> Int = { x, y -> x + y }

val PrintName: (String) -> Unit = { strName -> println("Hello, " + strName) }
val PrintName2 = { strName: String -> println("Android, " + strName) }

fun main() {

    println("These square of 5 value is:" + Square(5))
    println("These Add of 5 value is:" + add(5, 5))
    println("These Add of 10 value is:" + adds(10, 10))

    val result = ::sum
    val result1: (a: Int, b: Int) -> Int = ::sum
    println(result(5, 5))

    PrintName("World")
    PrintName2("Developer")


}