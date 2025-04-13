package Flow.lesson1

import kotlin.random.Random

fun main() {
    var filterCount = 0
    var mapCount = 0


    val list = mutableListOf<Int>().apply {
        repeat(1000) {
            add(Random.nextInt(1000))
        }
    }.asSequence() // поток данных


    list
        .filter {
            println("filter")
            filterCount++
            it % 2 == 0
        } // filterCount: 19
        .map {
            println("map")
            mapCount++
            it * 10
        } // mapCount: 10
        .take(10)
        .forEach(::println)

    println("filterCount: $filterCount")
    println("mapCount: $mapCount")
}