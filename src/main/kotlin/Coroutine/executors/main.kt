package Coroutine.executors

import java.util.concurrent.Executors

fun main() {
    val executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
    println(Runtime.getRuntime().availableProcessors().toString())
    repeat(100) {
        executorService.execute {
            Coroutine.executors.processImage(Coroutine.executors.Image(it))
        }
    }

}

private fun processImage(image: Coroutine.executors.Image) {
    println("Image $image is processing")
    Thread.sleep(1000)

    println("Image $image processed")
}

data class Image(val id: Int)