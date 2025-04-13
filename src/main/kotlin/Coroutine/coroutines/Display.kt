package Coroutine.coroutines

import Coroutine.entities.Author
import Coroutine.entities.Book
import kotlinx.coroutines.*
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import java.util.concurrent.Executors
import javax.swing.*
import javax.swing.WindowConstants.EXIT_ON_CLOSE
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext

object Display {

    private val dispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    private val scope = CoroutineScope(CoroutineName("Display coroutine scope") + Coroutine.coroutines.Display.dispatcher)
    // Создаем свой скоуп
    // Передаем составляющие CoroutineContext

    private val infoArea = JTextArea().apply {
        isEditable = false
    }

    private val loadButton = JButton("Load Book").apply {
        addActionListener {
            Coroutine.coroutines.Display.scope.launch {
                isEnabled = false
                Coroutine.coroutines.Display.infoArea.text = "Loading Book Information...\n"

                val book = Coroutine.coroutines.Display.loadBook()
                println("Loaded $book")
                Coroutine.coroutines.Display.infoArea.append("Book: ${book.title}\nYear: ${book.genre}\nGenre: ${book.genre}\n")
                Coroutine.coroutines.Display.infoArea.append("Loading Author Information...\n")

                val author = Coroutine.coroutines.Display.loadAuthor(book)
                println("Loaded $author")
                Coroutine.coroutines.Display.infoArea.append("Author ${author.name}\nBiography: ${author.biography}\n")

                isEnabled = true
            }
        }
    }


    private val timerLabel = JLabel("Time: 00:00")
    private val topPanel = JPanel(BorderLayout()).apply {
        add(Coroutine.coroutines.Display.timerLabel, BorderLayout.WEST)
        add(Coroutine.coroutines.Display.loadButton, BorderLayout.EAST)
    }

    private val mainFrame = JFrame("Book And Author Info").apply {
        layout = BorderLayout()

        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                super.windowClosing(e)
                Coroutine.coroutines.Display.scope.cancel()
            }
        })

        add(Coroutine.coroutines.Display.topPanel, BorderLayout.NORTH)
        add(JScrollPane(Coroutine.coroutines.Display.infoArea), BorderLayout.CENTER)
        size = Dimension(400, 300)


    }

    suspend fun show() {
        Coroutine.coroutines.Display.mainFrame.isVisible = true
        Coroutine.coroutines.Display.startTimer()
    }

    private fun longOperation() {
        mutableListOf<Int>().apply {
            repeat(300_000) {
                add(0, it)
            }
        }
    }

    private suspend fun loadBook(): Book {
        return withContext(Dispatchers.Default) {
            Coroutine.coroutines.Display.longOperation()
            val book = Book("1984", 1949, "Dystopia")
            book
        }
    }

    private suspend fun loadAuthor(book: Book): Coroutine.entities.Author {

        return withContext(Dispatchers.Default) {
            Coroutine.coroutines.Display.longOperation()
            val author = Coroutine.entities.Author("George Orwell", "British writer")
            author
        }

    }

    private suspend fun startTimer() {

        var totalSeconds = 0

        while (true) {
            val minutes = totalSeconds / 60
            val seconds = totalSeconds % 60
            Coroutine.coroutines.Display.timerLabel.text = String.format("Time: %02d:%02d", minutes, seconds)
            delay(1000)
            totalSeconds++
        }

    }

}