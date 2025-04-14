package Flow.dictionary

import kotlinx.coroutines.*
import java.awt.BorderLayout
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import java.util.concurrent.Executors
import javax.swing.*

object Display {
    private val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
    private val scope = CoroutineScope(dispatcher + SupervisorJob())
    private val repository = Repository
    private var loadingJob: Job? = null

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
        loadingJob?.cancel() // Если пользователь введёт еще один символ, то прошлую корутину нужно отменить
        loadingJob = scope.launch {
            resultArea.text = "Loading..."
            searchButton.isEnabled = false
            delay(500)

            val word = searchField.text.trim()
            val text = repository.loadDefinition(word).joinToString("\n\n")

            resultArea.text = text.ifBlank { "Text not found" }

            searchButton.isEnabled = true
        }
    }
    /* Есть проблема. Проблема в том, что функция выполняет множество разных операций
    Это делает невозможным применение данной функции в других частях кода
    Такая реализация программы называется императивный стиль

    Данную функцию нельзя называть чистой, поскольку она выполняет какие-то действия за её пределами
    Меняет какое-то внешнее состояние

    Проблема в том, что при работе с чистыми корутинами нам постоянно придется писать в таком стиле

    Flow позволяет писать программы в новом, реактивном стиле
    */



}