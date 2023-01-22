package com.example.app_semi_final.feature.drag_drop_words

import com.example.app_semi_final.util.modifiedAt

data class DragDropWordsText(
    val textFormat: String,
    val missingWords: List<String>,
    val answer: List<String>,
    val droppedWords: List<String> = emptyList(),
) {
    val placeholderStringLength = missingWords.maxOf { it.length }

    fun dropWord(word: String, index: Int): DragDropWordsText {
        return copy(
            missingWords = missingWords - word,
            droppedWords = if (index <= droppedWords.lastIndex) {
                droppedWords.modifiedAt(index) { word }
            } else buildList {
                addAll(droppedWords)
                while (size < index + 1) { add("") }
                set(index, word)
            }
        )
    }

    fun isSolved() = answer == droppedWords
}

const val PLACEHOLDER_MARK = "[PLACEHOLDER]"

val SampleText = DragDropWordsText(
    textFormat = "Текст $PLACEHOLDER_MARK несколькими пропусками $PLACEHOLDER_MARK вариантами.",
    missingWords = listOf("один", "два", "с", "и"),
    answer = listOf("с", "и")
)

val SampleText2 = DragDropWordsText(
    textFormat = "$PLACEHOLDER_MARK текст $PLACEHOLDER_MARK несколькими пропусками $PLACEHOLDER_MARK вариантами.",
    missingWords = listOf("Это", "один", "два", "с", "и"),
    answer = listOf("Это", "с", "и")
)
