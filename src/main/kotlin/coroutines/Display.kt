package coroutines

import entities.Author
import entities.Book
import kotlinx.coroutines.*
import java.awt.BorderLayout
import java.awt.Dimension
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import java.awt.event.WindowListener
import javax.swing.*
import javax.swing.WindowConstants.EXIT_ON_CLOSE
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext

object Display {

    private val scope = CoroutineScope(CoroutineName("Display coroutine scope"))
    // Создаем свой скоуп
    // Передаем составляющие CoroutineContext

    private val infoArea = JTextArea().apply {
        isEditable = false
    }

    private val loadButton = JButton("Load Book").apply {
        addActionListener {
            scope.launch {
                isEnabled = false
                infoArea.text = "Loading Book Information...\n"

                val book = loadBook()
                println("Loaded $book")
                infoArea.append("Book: ${book.title}\nYear: ${book.genre}\nGenre: ${book.genre}\n")
                infoArea.append("Loading Author Information...\n")

                val author = loadAuthor(book)
                println("Loaded $author")
                infoArea.append("Author ${author.name}\nBiography: ${author.biography}\n")

                isEnabled = true
            }
        }
    }


    private val timerLabel = JLabel("Time: 00:00")
    private val topPanel = JPanel(BorderLayout()).apply {
        add(timerLabel, BorderLayout.WEST)
        add(loadButton, BorderLayout.EAST)
    }

    private val mainFrame = JFrame("Book And Author Info").apply {
        layout = BorderLayout()

        addWindowListener(object : WindowAdapter() {
            override fun windowClosing(e: WindowEvent?) {
                super.windowClosing(e)
                scope.cancel()
            }
        })

        add(topPanel, BorderLayout.NORTH)
        add(JScrollPane(infoArea), BorderLayout.CENTER)
        size = Dimension(400, 300)


    }

    suspend fun show() {
        mainFrame.isVisible = true
        startTimer()
    }

    private suspend fun loadBook(): Book {
        delay(3000)
        val book = Book("1984", 1949, "Dystopia")
        return book
    }

    private suspend fun loadAuthor(book: Book): Author {
        delay(3000)
        val author = Author("George Orwell", "British writer")
        return author
    }

    private suspend fun startTimer() {

        var totalSeconds = 0

        while (true) {
            val minutes = totalSeconds / 60
            val seconds = totalSeconds % 60
            timerLabel.text = String.format("Time: %02d:%02d", minutes, seconds)
            delay(1000)
            totalSeconds++
        }

    }

}