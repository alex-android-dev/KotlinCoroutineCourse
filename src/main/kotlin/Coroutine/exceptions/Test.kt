package Coroutine.exceptions

import kotlinx.coroutines.*
import java.util.concurrent.Executors

private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
    println("Exception caught")
}
private val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
private val scope = CoroutineScope(Coroutine.exceptions.dispatcher + Coroutine.exceptions.exceptionHandler)

fun main() {
    Coroutine.exceptions.scope.launch {
        Coroutine.exceptions.method()
    }

    Coroutine.exceptions.scope.launch {
        Coroutine.exceptions.method2()
    }
}

private suspend fun method() {
    delay(3000)
    error("")
}

private suspend fun method2() {
    delay(5000)
    println("m2 is finished")
}