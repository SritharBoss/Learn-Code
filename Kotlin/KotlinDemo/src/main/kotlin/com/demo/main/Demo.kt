package com.demo.main


fun main() {
    val byte=5.toByte()
    val short=5.toShort()
    val int=5
    val long=100L
    val str="${"235C".toInt(16).toChar()} - aaAHIZz"
    val bool=true
    val char='C'

    val array= intArrayOf(1,2,3,4,6)
    val list= mutableListOf("One", "Two", "Three")
    val strArr=arrayOf("","")


    println(str.filter { it.code in 65..90 || it.code in 97..122 })

}

class MyClass(private val count: Int) : Runnable {
    override fun run() {
        println("Started :: $count :: ${Thread.currentThread().name}")
        try {
            Thread.sleep(5000)
        } catch (e: Exception) {
            TODO("Not yet implemented")
        }
        println("Completed :: $count :: ${Thread.currentThread().name}")
    }

}

fun <K> myFun(a: K): ArrayList<K> {
    return arrayListOf(a)
}

enum class Colors(private val id: Int) {
    RED(1), GREEN(2), YELLOW(3), WHITE(4);

    fun isRGB(): Boolean {
        return this.id in 1..3
    }
}