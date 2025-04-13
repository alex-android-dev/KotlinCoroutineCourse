package Flow.lesson3

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

private var lastIndex = 0
private val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
private val scope = CoroutineScope(dispatcher)

private suspend fun loadNext(): List<String> {
    delay(2000)
    return (lastIndex..<(lastIndex + 10))
        .map {
            "video: $it"
        }
        .also {
            lastIndex += 10
            println("Loaded: ${it.joinToString()}")
        }
    // Возвращаем 10 элементов и увеличиваем на 10
}

private suspend fun scroll(videos: List<String>) {
    delay(videos.size * 100L)
    println("Scrolled: ${videos.joinToString()}")
}

// Постраничная загрузка из интернета
fun main() {

    scope.launch {
        sequence {
            repeat(10) {
                val nextData: List<String> = loadNext()
                yield(nextData)
            }

        }.forEach {
            scroll(it)
        }
    }


}