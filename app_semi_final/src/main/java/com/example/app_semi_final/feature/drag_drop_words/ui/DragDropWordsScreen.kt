package com.example.app_semi_final.feature.drag_drop_words.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_semi_final.R
import com.example.app_semi_final.feature.drag_drop_words.DragDropWordsText
import com.example.app_semi_final.feature.drag_drop_words.DragDropWordsViewModel
import com.example.app_semi_final.feature.drag_drop_words.PLACEHOLDER_MARK
import com.example.app_semi_final.ui.theme.*
import com.example.app_semi_final.util.Draggable
import com.example.app_semi_final.util.DraggableReceiver
import com.example.app_semi_final.util.DraggingBox
import com.example.app_semi_final.util.LocalDragTargetInfo
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun DragDropWordsScreen() {
    val viewModel = viewModel<DragDropWordsViewModel>()
    val state by viewModel.state.collectAsState()

    DraggingBox {
        LazyColumn {
            items(state.content.size + 1) { index ->
                if (index == 0) {
                    Remark()
                } else {
                    val text = state.content[index - 1]

                    DragDropWordsTextUi(
                        text = text,
                        dropWord = { placeholderIndex, word ->
                            viewModel.dropWord(index - 1, word, placeholderIndex)
                        }
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
fun Remark() {
    Text(text = "сори не доделал(туть какой-то адовый баг")
}

@Composable
fun DragDropWordsTextUi(
    text: DragDropWordsText,
    dropWord: (placeholderIndex: Int, word: String) -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            HeaderAndStatus(text.isSolved())
            Spacer(modifier = Modifier.height(16.dp))
            TextFlow(text, dropWord)
            Spacer(modifier = Modifier.height(32.dp))
            WordsToDropFlow(text)
        }
    }
}

@Composable
fun HeaderAndStatus(solved: Boolean) {
    Row {
        Text(
            text = stringResource(id = R.string.drag_elements),
            style = Typography.body2.copy(color = TextSecondary),
            modifier = Modifier.weight(1f)
        )

        AnimatedVisibility(visible = solved) {
            Icon(
                painter = painterResource(id = R.drawable.ic_done),
                contentDescription = "",
                tint = DarkGreen
            )
        }
    }
}

@Composable
fun TextFlow(
    text: DragDropWordsText,
    dropWord: (i: Int, word: String) -> Unit
) {
    FlowRow(
        mainAxisSpacing = 2.dp,
        crossAxisSpacing = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        val textFragments = text.textFormat.split(PLACEHOLDER_MARK)
        textFragments.forEachIndexed { index, fragment ->
            TextFragment(fragment)
            if (index != textFragments.lastIndex) {
                val droppedWord = text.droppedWords.getOrNull(index)
                WordPlaceholder(
                    word = droppedWord ?: "",
                    placeholderStringLength = text.placeholderStringLength,
                    onWordDropped = { word ->
                        dropWord(index, word)
                    }
                )
            }
        }
    }
}

@Composable
fun WordPlaceholder(
    word: String,
    placeholderStringLength: Int,
    onWordDropped: (String) -> Unit
) {
    DraggableReceiver<String> { isDraggableWithinBounds, data ->
        val text = if (isDraggableWithinBounds) {
            LocalDragTargetInfo.current.dataToDrop as? String ?: ""
        } else {
            word
        }

        Word(imitatedLength = placeholderStringLength, text = text)
        if (data != null) {
            LaunchedEffect(key1 = data) {
                onWordDropped(data)
            }
        }
    }
}

@Composable
fun TextFragment(text: String) {
    Text(text = text, style = Typography.body1)
}

@Composable
fun WordToDrop(
    imitatedLength: Int,
    text: String,
) {
    Draggable(
        dataToDrop = text,
    ) {
        Word(imitatedLength, text)
    }
}

@Composable
private fun Word(imitatedLength: Int, text: String) {
    Surface(
        color = IdleOption,
        shape = RoundedCornerShape(4.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 2.dp)
        ) {
            val style = Typography.body1.copy()
            Text(
                text = "_".repeat(imitatedLength),
                style = style,
                color = IdleOption
            )

            Text(
                text = text,
                style = style,
                color = TextPrimary
            )
        }
    }
}

@Composable
fun WordsToDropFlow(text: DragDropWordsText) {
    FlowRow(
        mainAxisSpacing = 12.dp,
    ) {
        text.missingWords.forEach { missingWord ->
            WordToDrop(imitatedLength = text.placeholderStringLength, text = missingWord)
        }
    }
}

@Preview
@Composable
fun WordToDropPreview() {
    VKCupTheme {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            WordToDrop(imitatedLength = 4, text = "один")
            WordToDrop(imitatedLength = 4, text = "два")
            WordToDrop(imitatedLength = 4, text = "с")
            WordToDrop(imitatedLength = 4, text = "и")
            WordToDrop(imitatedLength = 4, text = "")
        }
    }
}