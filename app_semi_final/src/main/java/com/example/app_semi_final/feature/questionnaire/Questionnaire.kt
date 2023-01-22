package com.example.app_semi_final.feature.questionnaire

import com.example.app_semi_final.util.modifiedAt
import kotlinx.coroutines.flow.StateFlow

data class Questionnaire(
    val questions: List<Question>,
    val currentQuestionIndex: Int,
    val isCurrentQuestionAnswered: Boolean,
) {
    val currentQuestion: Question
        get() = questions[currentQuestionIndex]

    val canGoToNextQuestion: Boolean
        get() = isCurrentQuestionAnswered && currentQuestionIndex != questions.lastIndex

    fun markAnsweredWith(option: Option): Questionnaire {
        return this.copy(
            isCurrentQuestionAnswered = true,
            questions = questions.modifiedAt(currentQuestionIndex) {
                it.markPickedOption(option)
            }
        )
    }
}

data class Question(
    val title: String,
    val options: List<Option>,
) {
    fun markPickedOption(option: Option): Question {
        return this.copy(
            options = options.modifiedAt(options.indexOf(option)) {
                it.copy(isPicked = true)
            }
        )
    }
}

data class Option(
    val name: String,
    val isPicked: Boolean,
    val isCorrect: Boolean,
    val pickPercentage: Int,
)

val SampleQuestionnaire = Questionnaire(
    currentQuestionIndex = 0,
    isCurrentQuestionAnswered = false,
    questions = listOf(
        Question(
            title = "Вопрос?",
            options = listOf(
                Option(name = "ч т о", isPicked = false, isCorrect = false, pickPercentage = 0),
                Option(name = "что?", isPicked = false, isCorrect = true, pickPercentage = 69),
                Option(name = "чт", isPicked = false, isCorrect = false, pickPercentage = 0),
                Option(name = "че??", isPicked = false, isCorrect = false, pickPercentage = 0),
            )
        )
    )
)

val SampleQuestionnaireWithMultipleQuestions = Questionnaire(
    currentQuestionIndex = 0,
    isCurrentQuestionAnswered = false,
    questions = listOf(
        Question(
            title = "Первый вопрос из трёх",
            options = listOf(
                Option(name = "один", isPicked = false, isCorrect = true, pickPercentage = 100),
                Option(name = "два", isPicked = false, isCorrect = false, pickPercentage = 0),
                Option(name = "три", isPicked = false, isCorrect = false, pickPercentage = 0),
                Option(name = "четыре", isPicked = false, isCorrect = false, pickPercentage = 0),
            )
        ),
        Question(
            title = "Первый вопрос 2 - В тылу врага (второй вопрос)",
            options = listOf(
                Option(name = "пять", isPicked = false, isCorrect = true, pickPercentage = 25),
                Option(name = "шесть", isPicked = false, isCorrect = false, pickPercentage = 33),
                Option(name = "семь", isPicked = false, isCorrect = false, pickPercentage = 7),
                Option(name = "восемь", isPicked = false, isCorrect = false, pickPercentage = 10),
                Option(name = "девять", isPicked = false, isCorrect = false, pickPercentage = 25),
            )
        ),
        Question(
            title = "ТРЕТИЙ ВОПРОС!!!",
            options = listOf(
                Option(name = "десять", isPicked = false, isCorrect = true, pickPercentage = 146),
                Option(name = "одинадцать", isPicked = false, isCorrect = false, pickPercentage = 0),
                Option(name = "двенадцать", isPicked = false, isCorrect = false, pickPercentage = 0),
                Option(name = "тринадцать", isPicked = false, isCorrect = false, pickPercentage = 0),
            )
        ),
    )
)