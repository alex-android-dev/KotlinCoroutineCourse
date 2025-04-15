package Flow.coldFlows

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
            println("first flow: $it")
        }
    }

    scope.launch {
        flow
            .take(3)
            .collect {
                println("second flow: $it")
            }
    }
}

