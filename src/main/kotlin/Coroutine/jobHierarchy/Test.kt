package Coroutine.jobHierarchy

import kotlinx.coroutines.*
import java.util.concurrent.Executors

private val dispatcherIO = Executors.newCachedThreadPool().asCoroutineDispatcher()
private val parentJob = Job()
private val scope = CoroutineScope(dispatcherIO + parentJob)

fun main() {

    println(parentJob.toString())

    // Это одна уникальная корутина
    scope.launch {
        coroutineContext.job.parent?.let { println(it) }
        printNumber(1)
    }

    scope.launch {
        coroutineContext.job.parent?.let { println(it) }
        printNumber(2)
    }
    Thread.sleep(2000)
    parentJob.cancel()

}

private suspend fun printNumber(number: Int) {
    while (true) {
        println(number)
        delay(1000)
    }
}