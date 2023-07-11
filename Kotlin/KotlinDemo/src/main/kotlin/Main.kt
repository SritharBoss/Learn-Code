import java.lang.reflect.Field
import java.time.Duration
import java.util.*
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.seconds


class Main {
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

fun main() {
//    Main().printLocalVariables()
    Main().demoMethod()

}

open class Animal @JvmOverloads constructor(val name: String = "Default", val type: AnimalTypes = AnimalTypes.DEFAULT) {
    var leg: Int = 0;
}

enum class AnimalTypes {
    DOG, CAT, DEFAULT
}