package com.example.kotlindemo.Basic

class HighOrderFunction {
}

fun highOrder(func: () -> Unit) {
    func()


}

val Additions = { a: Int, b: Int -> a + b }

val printMe = { println("this line printed..!") }

fun main() {


    highOrder(printMe)
    highADd(Additions)

}

fun highADd(addfun: (Int, Int) -> Int) {
    println("the sum is:" + addfun(5, 4))
}