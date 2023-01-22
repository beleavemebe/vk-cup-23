package com.example.app_semi_final.feature.column_matching.ui

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app_semi_final.R
import com.example.app_semi_final.feature.column_matching.ColumnMatchingViewModel
import com.example.app_semi_final.feature.column_matching.MatchColumns
import com.example.app_semi_final.ui.library.AccentButton
import com.example.app_semi_final.ui.theme.*

@Composable
fun ColumnMatchingScreen() {
    val viewModel = viewModel<ColumnMatchingViewModel>()
    val state by viewModel.state.collectAsState()

    LazyColumn {
        items(state.content.size) { i ->
            val matchColumns = state.content[i]

            ColumnMatching(
                matchColumns = matchColumns,
                onValuesSwapped = { from, to ->
                    viewModel.swapValues(i, from, to)
                },
                onSendClicked = {
                    viewModel.checkKey(i)
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ColumnMatching(
    matchColumns: MatchColumns,
    onValuesSwapped: (Int, Int) -> Unit,
    onSendClicked: () -> Unit,
) {
    Surface(
        color = QuestionnaireCardBackground,
        shape = RoundedCornerShape(16.dp),
    ) {
        Column(
            modifier = Modifier.padding(20.dp)
        ) {
            Header(matchColumns)

            Spacer(modifier = Modifier.height(10.dp))

            Row {
                KeysColumn(matchColumns)
                ValuesColumn(matchColumns) { from: Int, to: Int ->
                    onValuesSwapped(from, to)
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.height(40.dp)
            ) {
                MatchCorrectness(matchColumns)

                Spacer(modifier = Modifier.weight(1f))

                SendButton(matchColumns, onSendClicked)
            }
        }
    }
}

@Composable
private fun Header(matchColumns: MatchColumns) {
    Text(
        text = stringResource(id = R.string.match_elements),
        style = Typography.body2.copy(color = TextSecondary),
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = matchColumns.question,
        style = Typography.h6.copy(color = TextPrimary)
    )
}

@Composable
private fun RowScope.KeysColumn(matchColumns: MatchColumns) {
    Column(modifier = Modifier.weight(1f)) {
        matchColumns.keys.forEach { text ->
            ColumnItem(
                text = text,
                isMatchedCorrectly = matchColumns.isCompleted,
                putPaddingOnTheEnd = true
            )
        }
    }
}

@Composable
private fun RowScope.ValuesColumn(
    matchColumns: MatchColumns,
    onValuesSwapped: (Int, Int) -> Unit,
) {
    val touchHelper = remember { ItemTouchHelper(ColumnAdapter.SwapCallback(onValuesSwapped)) }
    val columnAdapter = remember { ColumnAdapter(touchHelper) }
    AndroidView(
        modifier = Modifier.weight(1f),
        factory = { context ->
            RecyclerView(context).apply {
                touchHelper.attachToRecyclerView(this)
                adapter = columnAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    )

    LaunchedEffect(key1 = matchColumns.values) {
        columnAdapter.submitList(matchColumns.values)
    }

    LaunchedEffect(key1 = matchColumns.isCompleted) {
        columnAdapter.isMatchCompleted = matchColumns.isCompleted
    }
}


@Composable
fun ColumnItem(
    text: String,
    isMatchedCorrectly: Boolean,
    putPaddingOnTheEnd: Boolean,
    modifier: Modifier = Modifier,
    @DrawableRes endIconResId: Int? = null,
) {
    Column {
        val color by animateColorAsState(
            targetValue = if (isMatchedCorrectly) {
                CorrectOption
            } else {
                IdleOption
            }
        )

        val paddingModifier = if (putPaddingOnTheEnd) {
            Modifier.padding(end = 6.dp)
        } else {
            Modifier.padding(start = 6.dp)
        }

        Surface(
            color = color,
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .fillMaxWidth()
                .then(paddingModifier)
                .then(modifier)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text,
                    style = Typography.body1.copy(
                        color = TextPrimary,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp, vertical = 12.dp)
                )

                endIconResId?.let { resId ->
                    Icon(
                        painter = painterResource(id = resId),
                        contentDescription = "",
                        tint = Grey
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun DraggableColumnItem(
    text: String,
    isMatchedCorrectly: Boolean,
    startDragging: () -> Unit,
) {
    ColumnItem(
        text = text,
        isMatchedCorrectly = isMatchedCorrectly,
        putPaddingOnTheEnd = false,
        endIconResId = if (isMatchedCorrectly) null else R.drawable.ic_drag_handle,
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures(
                onPress = { startDragging() }
            )
        }
    )
}

@Composable
private fun RowScope.MatchCorrectness(matchColumns: MatchColumns) {
    AnimatedVisibility(
        visible = matchColumns.isSendButtonActive.not(),
        enter = fadeIn(), exit = fadeOut()
    ) {
        val completed = matchColumns.isCompleted
        val textResId = if (completed) R.string.correct else R.string.incorrect
        val color = if (completed) DarkGreen else DarkRed
        Text(
            text = stringResource(textResId),
            style = Typography.body2.copy(
                fontWeight = FontWeight.Medium,
                color = color
            ),
        )
    }
}

@Composable
private fun RowScope.SendButton(
    matchColumns: MatchColumns,
    onSendClicked: () -> Unit,
) {
    AnimatedVisibility(
        visible = matchColumns.isSendButtonActive,
        enter = fadeIn(), exit = fadeOut()
    ) {
        AccentButton(
            text = stringResource(id = R.string.send),
            onClick = onSendClicked
        )
    }
}
