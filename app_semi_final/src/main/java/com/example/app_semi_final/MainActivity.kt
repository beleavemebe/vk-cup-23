package com.example.app_semi_final

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_semi_final.feature.column_matching.ui.ColumnMatchingScreen
import com.example.app_semi_final.feature.drag_drop_words.ui.DragDropWordsScreen
import com.example.app_semi_final.feature.fill_blanks.ui.FillBlanksScreen
import com.example.app_semi_final.feature.questionnaire.ui.QuestionnairesScreen
import com.example.app_semi_final.feature.rating.ui.RatingScreen
import com.example.app_semi_final.ui.theme.Accent
import com.example.app_semi_final.ui.theme.Background
import com.example.app_semi_final.ui.theme.Grey
import com.example.app_semi_final.ui.theme.VKCupTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VKCupTheme {
                val viewModel = viewModel<MainViewModel>()
                val currentScreen by viewModel.screen.collectAsState()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Background,
                ) {
                    Column {
                        Box(modifier = Modifier.weight(1f)) {
                            when (currentScreen) {
                                Screen.QUESTIONNAIRES -> QuestionnairesScreen()
                                Screen.COLUMN_MATCHING -> ColumnMatchingScreen()
                                Screen.DRAG_DROP_WORDS -> DragDropWordsScreen()
                                Screen.FILL_WORDS -> FillBlanksScreen()
                                Screen.RATING -> RatingScreen()
                            }
                        }

                        BottomNavigation(
                            backgroundColor = Color.White
                        ) {
                            Screen.values().forEach { screen ->
                                val selected = screen == currentScreen
                                BottomNavigationItem(
                                    selected = selected,
                                    onClick = { viewModel.setScreen(screen) },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = screen.iconResId),
                                            contentDescription = null,
                                            tint = if (selected) Accent else Grey
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
