package com.example.kotlindemo.Basic

class CollectionMap {

    companion object {

        @JvmStatic
        fun main(array: Array<String>) {

            val marray = ArrayList<String>()
            marray.add("A")
            marray.add("B")
            marray[0] = "Android"
            marray.removeAt(1)
            println(marray.toString())
            println()
            println()
            println()


            val aMAp = mapOf<Any, String>(1 to "Tinu", "Tinu" to "Nidhi", true to "check")
            println(aMAp)

            val mMap = mutableMapOf<String, Int>()
            val mAMap = mutableMapOf("Apple" to 1, "Mango" to 2, "Banana" to 3)
            mMap.putAll(mAMap)
            mMap["Apple"] = 10
            println(mMap)


       


        }
    }
}