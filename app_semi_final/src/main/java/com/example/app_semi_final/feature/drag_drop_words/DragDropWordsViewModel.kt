package com.example.app_semi_final.feature.drag_drop_words

import androidx.lifecycle.ViewModel
import com.example.app_semi_final.util.modifiedAt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DragDropWordsViewModel : ViewModel() {
    private val _state = MutableStateFlow(createInitialState())
    val state = _state.asStateFlow()

    private fun createInitialState() = DragDropWordsState(
        content = buildList {
            repeat(5) {
                add(SampleText)
                add(SampleText2)
            }
        }
    )

    fun dropWord(elementIndex: Int, word: String, placeholderIndex: Int) {
        val oldContent = _state.value.content
        val newContent = oldContent.modifiedAt(elementIndex) { text ->
            text.dropWord(word, placeholderIndex)
        }

        _state.value = _state.value.copy(content = newContent)
    }
}