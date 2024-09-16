package com.example.kotlindemo.Basic

enum class EnumClass(val holiday: Boolean = false) {

    SUNDAY(true),
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY,
    FRIDAY,
    SATURDAY(true)
}

enum class DAYS { SUNDAY, MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY }


fun main() {


    println("Today is:" + EnumClass.WEDNESDAY)

    for (day in EnumClass.values()) {

        if (day.holiday) {
            println("" + day + " is Holiday")
        }
    }




}