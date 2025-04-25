package Flow.hot_flows

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

private val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher() // Не использует потоки демоны
private val scope = CoroutineScope(dispatcher)

fun main() {
    val flow = Repository.flow

    scope.launch {
        flow.collect {
            println("coroutine 1: $it")
        }
    }

    scope.launch {
        delay(5000)
        flow
            .take(3)
            .collect {
                println("coroutine 2: $it")
            }
    }
}

