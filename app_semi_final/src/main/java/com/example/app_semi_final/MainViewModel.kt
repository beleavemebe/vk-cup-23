package com.example.app_semi_final

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
    private val _screen = MutableStateFlow(Screen.QUESTIONNAIRES)
    val screen = _screen.asStateFlow()

    fun setScreen(screen: Screen) {
        _screen.value = screen
    }
}

enum class Screen(
    @DrawableRes val iconResId: Int
) {
    QUESTIONNAIRES(R.drawable.ic_questionnaire),
    COLUMN_MATCHING(R.drawable.ic_column_matching),
    DRAG_DROP_WORDS(R.drawable.ic_drag_drop_words),
    FILL_WORDS(R.drawable.ic_fill_words),
    RATING(R.drawable.ic_rating)
}
