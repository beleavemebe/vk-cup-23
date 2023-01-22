package com.example.app_semi_final.feature.fill_blanks

import androidx.lifecycle.ViewModel
import com.example.app_semi_final.util.modifiedAt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FillBlanksViewModel : ViewModel() {
    private val _state = MutableStateFlow(createInitialState())
    val state = _state.asStateFlow()

    private fun createInitialState() = FillBlanksState(
        content = buildList {
            repeat(10) {
                add(SampleTextWithBlanks)
            }
        }
    )

    fun updateBlankValue(elementIndex: Int, blankIndex: Int, value: String) {
        val oldContent = _state.value.content
        val newContent = oldContent.modifiedAt(elementIndex) { text ->
            text.updateBlankValue(blankIndex, value)
        }

        _state.value = _state.value.copy(content = newContent)
    }
}