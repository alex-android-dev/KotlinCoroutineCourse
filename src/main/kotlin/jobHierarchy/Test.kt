package jobHierarchy

import kotlin.concurrent.thread

fun main() {

    thread {
        repeat(5) {
            println(it)
            Thread.sleep(1000)
        }
    }

    thread(isDaemon = true) {
        while (true) {
            println("...")
            Thread.sleep(1000)
        }
    }


}