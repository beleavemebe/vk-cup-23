package com.example.app_qualification.feature.onboarding.ui

import com.example.app_qualification.feature.onboarding.Topic

data class OnboardingState(
    val topics: List<Topic>,
    val isContinueButtonVisible: Boolean,
)
