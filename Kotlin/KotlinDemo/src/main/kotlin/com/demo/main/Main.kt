package com.demo.main

import java.lang.reflect.Field
import java.util.*
import kotlin.time.Duration.Companion.hours

fun main() {
//    Main().printLocalVariables()
//    Main().demoMethod()

    val a=101
    val b=1
    val c=a or b

    println("$a\t--> "+"%32s".format(Integer.toBinaryString(a)).replace(" ","0").every(8))
    println("$b\t--> "+"%32s".format(Integer.toBinaryString(b)).replace(" ","0").every(8))

    println("$c\t--> "+"%32s".format(Integer.toBinaryString(c)).replace(" ","0").every(8))





}


fun String.every(digit:Int):String{
    val sb=StringBuilder()
    var a=this.length-1;
    while(a>=0){
        sb.append(this[a])
        if(a%digit==0 && a!=0){
            sb.append("_")
        }
        a--
    }

    return sb.toString().reversed()
}

class Main {

    companion object{
        fun add(a:Int,b:Int):Int{
            return a+b
        }
    }

    class Nested {
        public var nestedVar = "I'm Nested Variable, Can you access me?"
    }
    val int = 5
    val intArr: IntArray = intArrayOf(2, 5, 6, 4, 8, 9, 4, 3, 5)
    val array = arrayOf("d", "d")
    val str = "The quick brown fox jumps over the lazy dog"
    val list = mutableListOf("One", "Two", "Three")
    val map = hashMapOf("One" to 1)
    val range = 1..5;

    fun demoMethod(){
        println(int.hours)
    }

    fun printLocalVariables() {
        val inst = Main();

        val fields: Array<Field>? = inst.javaClass.declaredFields
        fields?.forEachIndexed { index, field ->
            run {
                field.isAccessible = true // To access private variables
                try {
                    val value: Any = field.get(inst)
                    if (value is IntArray || value is Array<*> || value is Collection<*>) {
                        val sj = StringJoiner(",");
                        when (value) {
                            is IntArray -> {
                                for (a in value) sj.add(a.toString())
                            }

                            is Array<*> -> {
                                for (a in value) sj.add(a.toString())
                            }

                            is Collection<*> -> {
                                for (a in value) sj.add(a.toString())
                            }
                        }
                        println("$index. ${field.name} --> $sj --> ${value::class.simpleName}")
                    } else {
                        println("$index. ${field.name} --> $value --> ${value::class.simpleName}")
                    }
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }
            }
        }
    }
}