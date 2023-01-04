package com.example.vkcup.feature.onboarding.ui

import com.example.vkcup.feature.onboarding.Topic

data class OnboardingState(
    val topics: List<Topic>,
    val isContinueButtonVisible: Boolean,
)
