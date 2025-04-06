package coroutines

import entities.Author
import entities.Book
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
            GlobalScope.launch {
                isEnabled = false
                infoArea.text = "Loading Book Information...\n"

                val book = loadBook()
                infoArea.append("Book: ${book.title}\nYear: ${book.genre}\nGenre: ${book.genre}\n")
                infoArea.append("Loading Author Information...\n")

                val author = loadAuthor(book)
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