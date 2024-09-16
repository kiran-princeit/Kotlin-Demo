package com.example.kotlindemo.Basic

class GenericClass<T>(values: T) {

    init {
        println("Values is: " + values)
        Check(values)
    }
}

fun main() {

    val genericClass = GenericClass(true)


}

fun <T> Check(text: T) {

    println("Received Values is: " + text)

}