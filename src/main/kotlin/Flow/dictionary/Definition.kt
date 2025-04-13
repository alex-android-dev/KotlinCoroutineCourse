package Flow.dictionary

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName


@Serializable
data class Definition(
    @SerialName("definition") val definition: String,
    @SerialName("word") val word: String,
    @SerialName("valid") val valid: Boolean
)