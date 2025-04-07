package cancellation

import kotlinx.coroutines.*
import java.util.concurrent.Executors


private val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
private val scope = CoroutineScope(dispatcher)


fun main() {

    val job = scope.launch {
        timer()
    }
    Thread.sleep(3000)
    job.cancel()
}


private suspend fun timer() {
    var second = 0

    while (true) {
        try {
            println("hello")
            println(second++)
            delay(1000) // delay умеет проверять активна ли корутина или нет
        } catch (cancellationException: CancellationException) {
            throw cancellationException // Пробрасываем исключение дальше наверх
        } catch (e: Exception) {

        }
    }
}