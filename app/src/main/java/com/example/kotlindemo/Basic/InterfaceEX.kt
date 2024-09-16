package com.example.kotlindemo.Basic

class InterfaceEX {
}


class ClasA : Add {

    override val firstno = 12
    override fun Add(a: Int, b: Int): Int {
        return a + b
    }

    override fun Add(a: Int, b: Int, c: Int): Int {
        return a + b + c
    }

}


class ClassB : Add {

    override val firstno = 21

    override fun Add(a: Int, b: Int): Int {
        return 2 * (a + b)
    }

    override fun Add(a: Int, b: Int, c: Int): Int {
        return 3 * (a + b + c)
    }

}

interface Add {

    val firstno: Int

    fun Add(a: Int, b: Int): Int

    fun Add(a: Int, b: Int, c: Int): Int

}

fun main() {

    val clasA = ClasA()
    val classB = ClassB()
    println("First Value is:" + clasA.firstno + " " + classB.firstno)
    println("Sec Value is:" + clasA.Add(5, 5) + " " + classB.Add(2, 2))
    println("third Value is:" + clasA.Add(5, 5, 5) + " " + classB.Add(2, 2, 2))


    val circle = Circle(0.5)
    val rectengle = Rectengle(4.0, 6.0)

    println("Circle Area:${circle.area()},Permeter: ${circle.permiter()}")
    println("Rectangle Area:${rectengle.area()},ParMeter:${rectengle.permiter()}")


}

interface shape {


    fun area(): Double
    fun permiter(): Double

}

class Circle(val radius: Double) : shape {

    override fun area(): Double {
        return 3.14 * radius * radius
    }

    override fun permiter(): Double {
        return 2 * 3.14 * radius
    }

}


class Rectengle(val width: Double, val height: Double) : shape {


    override fun area(): Double {
        return width * height
    }

    override fun permiter(): Double {
        return 2 * (width * height)
    }

}

