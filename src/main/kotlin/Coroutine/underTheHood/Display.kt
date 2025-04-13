package Coroutine.underTheHood

import Coroutine.entities.Author
import Coroutine.entities.Book
import java.awt.BorderLayout
import java.awt.Dimension
import javax.swing.*
import kotlin.concurrent.thread

object Display {

    private val infoArea = JTextArea().apply {
        isEditable = false
    }

    private val loadButton = JButton("Load Book").apply {
        addActionListener {
            loadData()
        }
    }


    private val timerLabel = JLabel("Time: 00:00")
    private val topPanel = JPanel(BorderLayout()).apply {
        add(timerLabel, BorderLayout.WEST)
        add(loadButton, BorderLayout.EAST)
    }

    private val mainFrame = JFrame("Book And Author Info").apply {
        layout = BorderLayout()
        add(topPanel, BorderLayout.NORTH)
        add(JScrollPane(infoArea), BorderLayout.CENTER)
        size = Dimension(400, 300)
    }

    fun show() {
        mainFrame.isVisible = true
        startTimer()
    }

    private fun loadData(
        stepEvent: Int = 0,
        data: Any? = null,
    ) {
        when (stepEvent) {
            0 -> {
                loadButton.isEnabled = false
                infoArea.text = "Loading Book Information...\n"
                loadBook { book ->
                    loadData(stepEvent = 1, data = book)
                }
            }

            1 -> {
                val book = data as Book
                infoArea.append("Book: ${book.title}\nYear: ${book.genre}\nGenre: ${book.genre}\n")
                infoArea.append("Loading Author Information...\n")

                loadAuthor(book) { author ->
                    loadData(stepEvent = 2, data = author)
                }
            }

            2 -> {
                val author = data as Coroutine.entities.Author
                infoArea.append("Author ${author.name}\nBiography: ${author.biography}\n")
                loadButton.isEnabled = true

            }
        }
    }

    private fun loadBook(callback: (Book) -> Unit) {
        thread {
            Thread.sleep(3000)
            val book = Book("1984", 1949, "Dystopia")
            callback(book)
            // Коллбэк передает лямбду в качестве параметра и внутри этой лямбды мы можем использовать книгу
        }
    }

    private fun loadAuthor(book: Book, callback: (Coroutine.entities.Author) -> Unit) {

        thread {
            Thread.sleep(3000)
            val author = Coroutine.entities.Author("George Orwell", "British writer")
            callback(author)
        }

    }

    private fun startTimer() {
        thread {
            var totalSeconds = 0

            while (true) {
                val minutes = totalSeconds / 60
                val seconds = totalSeconds % 60
                timerLabel.text = String.format("Time: %02d:%02d", minutes, seconds)
                Thread.sleep(1000)
                totalSeconds++
            }
        }
    }

}