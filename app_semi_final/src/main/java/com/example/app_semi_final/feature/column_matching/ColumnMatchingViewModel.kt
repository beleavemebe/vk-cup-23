package com.example.app_semi_final.feature.column_matching

import androidx.lifecycle.ViewModel
import com.example.app_semi_final.util.modifiedAt
import com.example.app_semi_final.util.swap
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ColumnMatchingViewModel : ViewModel() {
    private val _state = MutableStateFlow(createInitialState())
    val state = _state.asStateFlow()

    private fun createInitialState() = ColumnMatchingState(
        content = buildList {
            repeat(10) {
                add(SampleColumnMatching)
            }
        }
    )

    fun swapValues(elementIndex: Int, from: Int, to: Int) {
        val oldContent = state.value.content
        val newContent = oldContent.modifiedAt(elementIndex) {
            it.swapValues(from, to)
        }

        _state.value = _state.value.copy(content = newContent)
    }

    private fun MatchColumns.swapValues(
        from: Int,
        to: Int,
    ): MatchColumns {
        val newValues = values.toMutableList()
        newValues.swap(from, to)
        return copy(values = newValues.toList(), isSendButtonActive = true)
    }

    fun checkKey(elementIndex: Int) {
        val oldContent = state.value.content
        val matchColumns = oldContent[elementIndex]
        val newContent = oldContent.modifiedAt(elementIndex) {
            if (matchColumns.isMatchedCorrectly()) {
                it.copy(isCompleted = true, isSendButtonActive = false)
            } else {
                it.copy(isSendButtonActive = false)
            }
        }

        _state.value = _state.value.copy(content = newContent)
    }
}