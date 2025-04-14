package Flow.dictionary

import kotlinx.coroutines.*
import kotlinx.serialization.json.Json
import java.net.HttpURLConnection
import java.net.URI
import java.util.concurrent.Executors

object Repository {
    private const val BASE_URL = "https://api.api-ninjas.com/v1/dictionary?word="
    private const val API_KEY = "yYFS9E2t7L5skBLjnBMyYQ==lJfkGarA7EQTOPYu"
    private const val HEADER_KEY = "X-Api-Key"

    private val jsonWithIgnoreKeys = Json { ignoreUnknownKeys = true }

    suspend fun loadDefinition(word: String): List<String> {

        return withContext(Dispatchers.IO) {

            var connection: HttpURLConnection? = null

            try {
                val urlString = BASE_URL + word // адрес

                val url = URI.create(urlString).toURL() // Преобразуем в объект URL

                connection = (url.openConnection() as HttpURLConnection).apply {
                    addRequestProperty(
                        HEADER_KEY, API_KEY
                    )
                }
                // Переходим по адресу, открывая соединение
                // Приводим к HttpURLConnection, чтобы можно было читать данные от сервера
                // Передаем сюда заголовок с токеном

                val response = connection.inputStream.bufferedReader().readText()
                // Получаем ответ от сервера.
                // Поток байтов, который преобразуем в поток символов
                // JSON

                jsonWithIgnoreKeys.decodeFromString<Definition>(response).mapDefinitionToList()
            } catch (e: Exception) {
                listOf()
            } finally {
                connection?.disconnect()
            }
        }

    }

    private fun Definition.mapDefinitionToList(): List<String> {
        val regex = Regex("\\d. ")
        return this.definition.split(regex).map { it.trim() }.filter { it.isNotEmpty() }
    }
}
// Открыть соединение с интернетом
// Сделать запрос
// Прочитать данные от сервер
// Полученный объект преобразовать в Definition
