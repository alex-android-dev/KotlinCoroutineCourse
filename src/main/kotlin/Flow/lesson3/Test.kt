package Flow.lesson3

private var lastIndex = 0

private fun loadNext(): List<String> {
    Thread.sleep(2000)
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

private fun scroll(videos: List<String>) {
    Thread.sleep(videos.size * 100L)
    println("Scrolled: ${videos.joinToString()}")
}

// Постраничная загрузка из интернета
fun main() {

    sequence {
        repeat(10) {
            val nextData: List<String> = loadNext()
            yield(nextData)
        }

    }.forEach {
        scroll(it)
    }


}