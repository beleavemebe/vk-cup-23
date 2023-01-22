package com.example.app_semi_final.feature.fill_blanks

import com.example.app_semi_final.util.modifiedAt

const val BLANK_MARK = "BLANK"

data class TextWithBlanks(
    val text: String,
    val answer: List<String>,
    val blanksValues: List<String> = answer.map { "" }
) {
    val correctText = run {
        val iterator = answer.iterator()
        text.replace(BLANK_MARK.toRegex()) {
            iterator.next()
        }
    }

    fun updateBlankValue(blankIndex: Int, value: String): TextWithBlanks {
        return copy(
            blanksValues = blanksValues.modifiedAt(blankIndex) { prevValue ->
                if (value.length <= answer[blankIndex].length) value else prevValue
            }
        )
    }

    fun isCorrect() = answer == blanksValues
}

val SampleTextWithBlanks = TextWithBlanks(
    text = "Текст $BLANK_MARK несколькими пропусками $BLANK_MARK вариантами.",
    answer = listOf("с", "и"),
)

fun main() {
    println(SampleTextWithBlanks.correctText)
}