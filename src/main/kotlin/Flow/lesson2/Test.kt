package Flow.lesson2

import kotlin.random.Random

fun main() {
    var filterCount = 0
    var mapCount = 0


    sequence {
        println("Start generation counts...")
        yield(0)
        println("Generate random numbers")
        repeat(100) {
            yield(Random.nextInt(1000))
        }
    } // Приостановит дальнейшее выполнение кода, полетит по цепочке. После того как дойдет до терминального оператора - выведется и все начнется заново
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