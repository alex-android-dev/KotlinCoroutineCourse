package Coroutine.supervisorJob

import kotlinx.coroutines.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

private val parentJob = SupervisorJob()
private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
    println("Exception Caught")
}
private val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
private val scope = CoroutineScope(Coroutine.supervisorJob.parentJob + Coroutine.supervisorJob.exceptionHandler + Coroutine.supervisorJob.dispatcher)


fun main() {

    Coroutine.supervisorJob.scope.launch {
        Coroutine.supervisorJob.longOperation(3000, 1)
        error("")
    }

    Coroutine.supervisorJob.scope.launch {
        Coroutine.supervisorJob.longOperation(4000, 2)
    }


}

private suspend fun longOperation(timeMillis: Long, number: Int) {
    println("Started number: $number")
    delay(timeMillis)
    println("Finish number: $number")
}