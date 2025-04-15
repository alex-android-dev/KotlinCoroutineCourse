
package Flow.dictionary

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.awt.BorderLayout
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.util.concurrent.Executors
import javax.swing.*

@Suppress("OPT_IN_USAGE")
object Display {
    private val queries = Channel<String>()


    private val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
    private val scope = CoroutineScope(dispatcher + SupervisorJob())
    private val repository = Repository

    private val enterWordLAbel = JLabel("Enter word: ")

    private val searchField = JTextField(20).apply {
        addKeyListener(object : KeyAdapter() {
            override fun keyReleased(e: KeyEvent?) {
                super.keyReleased(e)
                loadDefinition()
            }
        })
    }

    private val searchButton = JButton("Search").apply {
        addActionListener {
            loadDefinition()
        }
    }

    private val resultArea = JTextArea(20, 50).apply {
        isEditable = false
        lineWrap = true
    }

    private val topPanel = JPanel().apply {
        add(enterWordLAbel)
        add(searchField)
        add(searchButton)
    }

    private val mainFrame = JFrame("Dictionary App").apply {
        layout = BorderLayout()
        add(topPanel, BorderLayout.NORTH)
        add(JScrollPane(resultArea), BorderLayout.CENTER)
        pack()
    }

    fun show() {
        mainFrame.isVisible = true
    }

    private fun loadDefinition() {
        scope.launch {
            queries.send(searchField.text.trim())
        }
    }

    init {
        queries.consumeAsFlow() // Преобразует канал в Flow
            .onEach {
                resultArea.text = "Loading..."
                searchButton.isEnabled = false
            } // Вызывается перед стартом потока. В данном случае делаем кнопки неактивными
            .debounce(500)
            .map {
                repository.loadDefinition(it)
            } // Сразу же загружаем слово, которое летит по цепочке. Нам прилетает уже ответ от сервера тут
            .map {
                it.joinToString("\n\n").ifEmpty { "Text not found" }
            }
            .onEach {
                println(it)
                resultArea.text = it
                searchButton.isEnabled = true
            } // Данные действия нужно выполнять при каждом новом объекте в потоке
            .launchIn(scope) // У скоупа вызывает метод launch и у Flow вызывает метод collect
    }

}