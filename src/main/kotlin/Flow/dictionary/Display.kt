package Flow.dictionary

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import java.awt.BorderLayout
import java.util.concurrent.Executors
import javax.swing.*

object Display {
    private val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
    private val scope = CoroutineScope(dispatcher + SupervisorJob())
    private val repository = Repository

    private val enterWordLAbel = JLabel("Enter word: ")
    private val searchField = JTextField(20)

    private val searchButton = JButton("Search").apply {
        addActionListener {
            scope.launch {
                resultArea.text = "Loading..."
                isEnabled = false
                val word = searchField.text.trim()
                val text = repository.loadDefinition(word).joinToString("\n\n")

                resultArea.text = text.ifBlank { "Text not found" }

                isEnabled = true
            }

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

}