package com.example.kotlindemo.Basic


abstract class Employee(val name: String,val experience: Int) {   // Non-Abstract
    // Property
    // Abstract Property (Must be overridden by Subclasses)
    abstract var salary: Double

    // Abstract Methods (Must be implemented by Subclasses)
    abstract fun dateOfBirth(date:String)

    // Non-Abstract Method
    fun employeeDetails() {
        println("Name of the employee: $name")
        println("Experience in years: $experience")
        println("Annual Salary: $salary")
        println()
        println()
    }
}

class Engineer(name: String,experience: Int) : Employee(name,experience) {
    override var salary = 500000.00
    override fun dateOfBirth(date:String){
        println("Date of Birth is: $date")
    }
}
fun main(args: Array<String>) {
    val eng = Engineer("Daivik",2)
    eng.employeeDetails()
    eng.dateOfBirth("02 December 2000")


    var add: Calculator = Addition()
    var x1 = add.cal(4, 6)
    println("Addition of two numbers $x1")
    var sub: Calculator = Sub()
    var x2 = sub.cal(10,6)
    println("Subtraction of two numbers $x2")
    var mul: Calculator = Mul()
    var x3 = mul.cal(20,6)
    println("Multiplication of two numbers $x3")
}


// abstract class
abstract class Calculator {
    abstract fun cal(x: Int, y: Int) : Int
}
// addition of two numbers
class Addition : Calculator() {
    override fun cal(x: Int, y: Int): Int {
        return x + y
    }
}
// subtraction of two numbers
class Sub : Calculator() {
    override fun cal(x: Int, y: Int): Int {
        return x - y
    }
}
// multiplication of two numbers
class Mul : Calculator() {
    override fun cal(x: Int, y: Int): Int {
        return x * y
    }

}



