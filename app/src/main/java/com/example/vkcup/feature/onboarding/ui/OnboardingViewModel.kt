package com.example.vkcup.feature.onboarding.ui

import androidx.lifecycle.ViewModel
import com.example.vkcup.feature.onboarding.Topic
import com.example.vkcup.feature.onboarding.TopicsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OnboardingViewModel : ViewModel() {
    private val topicsRepository = TopicsRepository()

    private val _state = MutableStateFlow(createInitialState())
    val state = _state.asStateFlow()

    private fun createInitialState() = OnboardingState(
        topics = topicsRepository.fetchTopics(),
        isContinueButtonVisible = false
    )

    fun onTopicClicked(topic: Topic) {
        val oldList = _state.value.topics
        val index = oldList.indexOf(topic)
        val toggledTopic = toggleTopic(topic)
        val newList = buildList {
            addAll(oldList)
            set(index, toggledTopic)
        }

        val didUserPickTheTopic = toggledTopic.isPicked
        if (didUserPickTheTopic) {
            val similarTopics = topicsRepository.getSimilarTopics(topic)
            refreshTopics(newList + similarTopics)
        } else {
            refreshTopics(newList)
        }
    }

    private fun toggleTopic(topic: Topic): Topic {
        return topic.copy(isPicked = !topic.isPicked)
    }

    private fun refreshTopics(
        newList: List<Topic>
    ) {
        _state.value = _state.value.copy(
            topics = newList,
            isContinueButtonVisible = newList.any { it.isPicked }
        )
    }
}
