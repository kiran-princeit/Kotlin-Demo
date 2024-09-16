package com.example.kotlindemo.Basic

class PolyMorePhysum : ClassOne() {
    override var name: String = "Manya"

    override fun Add(a: Int, b: Int): Int {
        val sum = super.Add(a, b)
        val squ = sum * sum
        return squ
    }
}

open class ClassOne { //Parent Class

    open var name = "Daivik"
    open fun Add(a: Int, b: Int): Int {
        return a + b
    }

}


fun main() {

    var classOne = ClassOne()
    var classTwo = PolyMorePhysum()
    println("The name Of Poly:" + classOne.name + " " + classTwo.name)
    println("The Addition Of Poly:" + classOne.Add(9, 0) + " " + classTwo.Add(1, 8))

    val animals = listOf(Animal(), Dog(), Cat())
    for (animal in animals)
        animal.makesound()
}


//super Class
open class Animal {
    open fun makesound() {
        println("Animal makes a sound")
    }
}


// sub Class : 1
class Dog : Animal() {
    override fun makesound() {
        println("Dog barks")
    }
}

// sub Class : 2

class Cat : Animal() {

    override fun makesound() {
        println("Cat meows")
    }
}