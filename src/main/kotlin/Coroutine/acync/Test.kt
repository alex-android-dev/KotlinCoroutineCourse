package Coroutine.acync

import kotlinx.coroutines.*
import java.util.concurrent.Executors

private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
    println("Exception caught")
}
private val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
private val superVisorJob = SupervisorJob()
private val scope = CoroutineScope(dispatcher + superVisorJob + exceptionHandler)

fun main() {

    scope.launch {
        async {
            method()
        }
    }

    scope.launch {
        method2()
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