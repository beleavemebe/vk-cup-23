package com.example.app_semi_final.feature.questionnaire

import androidx.lifecycle.ViewModel
import com.example.app_semi_final.util.modifiedAt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class QuestionnaireViewModel : ViewModel() {
    private val _state = MutableStateFlow(createInitialState())
    val state = _state.asStateFlow()

    private fun createInitialState() = QuestionnaireState(
        content = buildList {
            repeat(5) {
                add(SampleQuestionnaire)
                add(SampleQuestionnaireWithMultipleQuestions)
            }
        }
    )

    fun pickOption(elementIndex: Int, option: Option) {
        val oldContent = _state.value.content
        val questionnaire = oldContent[elementIndex]
        if (questionnaire.isCurrentQuestionAnswered) return

        val newContent = oldContent.modifiedAt(elementIndex) {
            it.markAnsweredWith(option)
        }

        _state.value = _state.value.copy(content = newContent)
    }

    fun showNextQuestion(questionnaire: Questionnaire) {
        require(questionnaire.isCurrentQuestionAnswered) {
            "Trying to go onto the next question while the current one is not answered."
        }

        val oldContent = _state.value.content
        val iOfQuestionnaire = oldContent.indexOf(questionnaire)
        val newContent = oldContent.modifiedAt(iOfQuestionnaire) {
            it.copy(currentQuestionIndex = it.currentQuestionIndex + 1, isCurrentQuestionAnswered = false)
        }

        _state.value = _state.value.copy(content = newContent)
    }
}