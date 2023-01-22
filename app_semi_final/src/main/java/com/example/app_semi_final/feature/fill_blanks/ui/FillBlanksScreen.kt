package com.example.app_semi_final.feature.fill_blanks.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_semi_final.R
import com.example.app_semi_final.feature.fill_blanks.BLANK_MARK
import com.example.app_semi_final.feature.fill_blanks.FillBlanksViewModel
import com.example.app_semi_final.feature.fill_blanks.TextWithBlanks
import com.example.app_semi_final.ui.theme.DarkGreen
import com.example.app_semi_final.ui.theme.TextSecondary
import com.example.app_semi_final.ui.theme.Typography
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun FillBlanksScreen() {
    val viewModel = viewModel<FillBlanksViewModel>()
    val state by viewModel.state.collectAsState()

    LazyColumn {
        items(state.content.size) { index ->
            val element = state.content[index]

            FillBlanks(
                textWithBlanks = element,
                updateBlankValue = { blankIndex, value ->
                    viewModel.updateBlankValue(index, blankIndex, value)
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun FillBlanks(
    textWithBlanks: TextWithBlanks,
    updateBlankValue: (i: Int, value: String) -> Unit,
) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            HeaderAndStatus(textWithBlanks.isCorrect())

            Spacer(modifier = Modifier.height(16.dp))

            FlowRow {
                val textFragments = textWithBlanks.text.split(BLANK_MARK)
                textFragments.forEachIndexed { index, text ->
                    Text(text = text)
                    if (index != textFragments.lastIndex) {
                        Blank(textWithBlanks, index, updateBlankValue)
                    }
                }
            }
        }
    }

}

@Composable
private fun Blank(
    textWithBlanks: TextWithBlanks,
    index: Int,
    updateBlankValue: (i: Int, value: String) -> Unit,
) {
    Box(Modifier.width(IntrinsicSize.Min)) {
        Text(text = "_".repeat(textWithBlanks.answer[index].length))
        BasicTextField(
            value = textWithBlanks.blanksValues[index],
            onValueChange = {
                updateBlankValue(index, it)
            },
        )
    }
}

@Composable
fun HeaderAndStatus(solved: Boolean) {
    Row {
        Text(
            text = stringResource(id = R.string.rate_post),
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
