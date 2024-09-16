package com.example.kotlindemo.Basic

class ScopeFunction {

    var name: String = ""
    var age = 0
    var mobNo: String = " "

}

fun main() {

    var name: String? = "abcd"
    name?.let {
            println("the length of the name is: " + it.length)

    }


    val scopefun = ScopeFunction().apply {

        name = "JOHN"
        age = 25;
        mobNo = "9876543212"

    }

    val age = with(scopefun) {

        println("the name of User is: " + name)
        age

    }

    println("the age of user is: " + age)

    scopefun.also {
        it.name = "Manya"
        println("the name hase changed..!")
    }
    println("the name is: " + scopefun.name)


}