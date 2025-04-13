package Flow.dictionary

import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL

object Repository {
    private const val BASE_URL = "https://api.api-ninjas.com/v1/dictionary?word="
    private const val API_KEY = "yYFS9E2t7L5skBLjnBMyYQ==lJfkGarA7EQTOPYu"
    private const val HEADER_KEY = "X-Api-Key"

    fun loadDefinition(word: String): Definition {
        val urlString = BASE_URL + word // адрес

        val url = URI.create(urlString).toURL() // Преобразуем в объект URL

        val urlConnection = (url.openConnection() as HttpURLConnection).apply {
            addRequestProperty(
                HEADER_KEY, API_KEY
            )
        }
        // Переходим по адресу, открывая соединение
        // Приводим к HttpURLConnection, чтобы можно было читать данные от сервера
        // Передаем сюда заголовок с токеном


        val response = urlConnection.inputStream.bufferedReader().readText()
        // Получаем ответ от сервера.
        // Поток байтов, который преобразуем в поток символов
        // JSON

        return Json.decodeFromString(response)


    }

    // Открыть соединение с интернетом
    // Сделать запрос
    // Прочитать данные от сервер
    // Полученный объект преобразовать в Definition
}

fun main() {
    while (true) {
        println("Enter word: ")

        try {
            val word = readln()

            val definition = Repository.loadDefinition(word)

            println(definition.definition)
        }catch (e: Exception) {
            println("error")
        }


    }
}