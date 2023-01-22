package com.example.app_semi_final.feature.questionnaire.ui

import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_semi_final.R
import com.example.app_semi_final.feature.questionnaire.*
import com.example.app_semi_final.ui.theme.*

@Composable
fun QuestionnairesScreen() {
    val viewModel = viewModel<QuestionnaireViewModel>()
    val state by viewModel.state.collectAsState()

    LazyColumn {
        items(state.content.size) { i ->
            val questionnaire = state.content[i]

            QuestionnaireCard(
                pQuestionnaire = questionnaire,
                onOptionPicked = { option ->
                    viewModel.pickOption(i, option)
                },
                onNextClicked = {
                    viewModel.showNextQuestion(questionnaire)
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun QuestionnaireCard(
    pQuestionnaire: Questionnaire,
    onOptionPicked: (Option) -> Unit,
    onNextClicked: () -> Unit
) {
    Surface(
        color = QuestionnaireCardBackground,
        shape = RoundedCornerShape(16.dp),
    ) {
        AnimatedContent(
            targetState = pQuestionnaire,
            transitionSpec = { EnterTransition.None with ExitTransition.None }
        ) { questionnaire ->
            Column(modifier = Modifier.padding(20.dp)) {
                QuestionInfo(questionnaire, onNextClicked)

                Spacer(modifier = Modifier.height(16.dp))

                Options(
                    options = questionnaire.currentQuestion.options,
                    showStats = questionnaire.isCurrentQuestionAnswered,
                    onOptionPicked = onOptionPicked
                )
            }
        }
    }
}

@Composable
fun QuestionInfo(
    questionnaire: Questionnaire,
    onNextClicked: () -> Unit,
) {
    val question = questionnaire.currentQuestion
    val questionIndex = questionnaire.currentQuestionIndex
    val totalQuestions = questionnaire.questions.size

    Row {
        Text(
            text = stringResource(R.string.question_placeholder, questionIndex + 1, totalQuestions),
            style = Typography.body2.copy(color = TextSecondary),
            modifier = Modifier.weight(1f),
        )

        AnimatedVisibility(
            visible = questionnaire.canGoToNextQuestion,
            enter = fadeIn(), exit = ExitTransition.None
        ) {
            Text(
                text = "Далее",
                style = Typography.body2.copy(
                    color = Accent,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.clickable { onNextClicked() }
            )
        }
    }

    Spacer(modifier = Modifier.height(12.dp))

    Text(
        text = question.title,
        style = Typography.h6.copy(color = TextPrimary)
    )
}

@Composable
fun Options(
    options: List<Option>,
    showStats: Boolean,
    onOptionPicked: (Option) -> Unit,
) {
    options.forEachIndexed { index, option ->
        SingleOption(
            option = option,
            showStats = showStats,
            onClick = {
                onOptionPicked(option)
            }
        )

        if (index != options.lastIndex) {
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SingleOption(
    option: Option,
    showStats: Boolean,
    onClick: () -> Unit
) {
    val color by animateColorAsState(
        if (!showStats) {
            IdleOption
        } else if (option.isCorrect) {
            CorrectOption
        } else if (option.isPicked) {
            WrongOption
        } else {
            IdleOption
        }
    )

    Surface(
        color = color,
        shape = RoundedCornerShape(8.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            Text(
                text = option.name,
                style = Typography.body1.copy(
                    color = TextPrimary,
                    fontWeight = if (showStats && option.isCorrect) {
                        FontWeight.Bold
                    } else {
                        FontWeight.Normal
                    }
                ),
                modifier = Modifier.weight(1f)
            )

            if (showStats) {
                if (option.isCorrect) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_done),
                        contentDescription = "",
                        tint = DarkGreen
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "${option.pickPercentage}%",
                    style = Typography.body1.copy(
                        color = TextPrimary,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
@Preview
fun QuestionnaireCardPreview() = VKCupTheme {
    QuestionnaireCard(pQuestionnaire = SampleQuestionnaire, {}, {})
}
