package com.example.kotlindemo.Basic

class CollectionList {

    companion object {
        @JvmStatic
        fun main(arg: Array<String>) {

            val alist = listOf("A", "2", "C", "4", "E", "F", listOf(1,2,3))
            println(alist)

            val mlist = mutableListOf<Any>("A", "B")
            mlist.add(0, "H")
            mlist.add("G")
            mlist.add(1)

            val mAlist = mutableListOf<Any>("Z", "X", false)
            mlist.addAll(mAlist)
            println()
            println(mlist)
        }
    }
}