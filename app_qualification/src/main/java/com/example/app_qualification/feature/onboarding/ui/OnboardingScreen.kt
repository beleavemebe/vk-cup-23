package com.example.app_qualification.feature.onboarding.ui

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.app_qualification.R
import com.example.app_qualification.feature.onboarding.Topic
import com.example.app_qualification.ui.library.DarkButton
import com.example.app_qualification.ui.library.Chip
import com.example.app_qualification.ui.library.WhiteButton
import com.example.app_qualification.ui.theme.*
import com.google.accompanist.flowlayout.FlowRow

@Composable
fun TopicsScreen() {
    val viewModel = viewModel<OnboardingViewModel>()
    val state by viewModel.state.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Header()

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        TopicsWall(
            topics = state.topics,
            onTopicClicked = viewModel::onTopicClicked
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        AnimatedContinueButtonWithPlaceholder(state.isContinueButtonVisible)
    }
}

@Composable
fun ColumnScope.AnimatedContinueButtonWithPlaceholder(
    isContinueButtonVisible: Boolean
) {
    val columnScope = this@AnimatedContinueButtonWithPlaceholder
    Box(contentAlignment = Alignment.Center) {
        columnScope.AnimatedVisibility(
            visible = isContinueButtonVisible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            ContinueButton()
        }

        columnScope.AnimatedVisibility(
            visible = isContinueButtonVisible.not(),
            enter = EnterTransition.None,
            exit = ExitTransition.None
        ) {
            Spacer(modifier = Modifier.height(80.dp))
        }
    }
}

@Preview
@Composable
fun HeaderPreview() {
    VKCupTheme {
        Header()
    }
}

@Composable
fun Header() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
    ) {
        Text(
            text = stringResource(R.string.onboarding_header_text),
            color = TextSecondary,
            modifier = Modifier.weight(1f),
        )

        Spacer(modifier = Modifier.width(12.dp))

        DarkButton(
            text = stringResource(R.string.later)
        ) {
        }
    }
}

@Composable
fun TopicsWall(
    topics: List<Topic>,
    onTopicClicked: (Topic) -> Unit
) {
    FlowRow(
        mainAxisSpacing = 8.dp,
        crossAxisSpacing = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
    ) {
        topics.forEach { topic ->
            TopicChip(topic = topic, onClick = onTopicClicked)
        }
    }
}

@Composable
fun TopicChip(
    topic: Topic,
    onClick: (Topic) -> Unit
) {
    Chip(
        isActive = topic.isPicked,
        chipText = topic.name
    ) {
        onClick(topic)
    }
}

@Preview
@Composable
fun InactiveTopicChipPreview() {
    VKCupTheme {
        TopicChip(topic = Topic("Inactive Topic", false)) {}
    }
}

@Preview
@Composable
fun ActiveTopicChipPreview() {
    VKCupTheme {
        TopicChip(topic = Topic("Active Topic", true)) {}
    }
}

@Composable
fun ContinueButton() {
    WhiteButton(
        text = stringResource(R.string.btn_continue),
        cornerSize = CornerSize(40.dp),
        modifier = Modifier
            .width(200.dp)
            .height(80.dp)
    ) {}
}

@Preview
@Composable
fun ContinueButtonPreview() {
    VKCupTheme {
        ContinueButton()
    }
}
