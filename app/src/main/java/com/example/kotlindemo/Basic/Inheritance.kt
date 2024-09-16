package com.example.kotlindemo.Basic

class Inheritance : ClassA() {


}

fun main() {
    val classB = Inheritance()

    println("The name is :" + classB.nm)
    println("The sum is :" + classB.Add(5, 4))

}


open class ClassA {

    val nm = "Manya"

    fun Add(a: Int, b: Int): Int {
        return a + b
    }

}

