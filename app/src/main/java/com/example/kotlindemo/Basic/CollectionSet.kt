package com.example.kotlindemo.Basic

class CollectionSet {

    companion object {

        @JvmStatic

        fun main(args: Array<String>) {


            val aSet = setOf("Apple", 9, 8, "0","Greps")
            println(aSet)

            val mset = mutableSetOf("Dev", "MAnu", 1, false)
            mset.add(true)

            println()

            println(mset)
        }
    }
}