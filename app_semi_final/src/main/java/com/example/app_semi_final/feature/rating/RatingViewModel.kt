package com.example.app_semi_final.feature.rating

import androidx.lifecycle.ViewModel
import com.example.app_semi_final.util.modifiedAt
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RatingViewModel : ViewModel() {
    private val _state = MutableStateFlow(createInitialState())
    val state = _state.asStateFlow()

    private fun createInitialState() = RatingState(
        content = buildList {
            repeat(15) {
                add(Rating(0))
            }
        }
    )

    fun changeRating(elementIndex: Int, rating: Int) {
        val oldContent = _state.value.content
        val newContent = oldContent.modifiedAt(elementIndex) {
            it.copy(stars = rating)
        }

        _state.value = _state.value.copy(content = newContent)
    }
}