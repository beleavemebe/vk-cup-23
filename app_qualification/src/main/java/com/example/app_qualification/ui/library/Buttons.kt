package com.example.app_qualification.ui.library

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.app_qualification.ui.theme.DarkButtonBackground
import com.example.app_qualification.ui.theme.TextPrimary
import com.example.app_qualification.ui.theme.Typography

private object DarkRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor(): Color = Color.Black

    @Composable
    override fun rippleAlpha(): RippleAlpha = RippleTheme.defaultRippleAlpha(
        contentColor = Color.Black, lightTheme = isSystemInDarkTheme()
    )
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun WhiteButton(
    text: String,
    cornerSize: CornerSize,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    CompositionLocalProvider(
        LocalRippleTheme provides DarkRippleTheme
    ) {
        Surface(
            selected = false,
            onClick = { onClick() },
            shape = RoundedCornerShape(cornerSize),
            color = Color.White,
            modifier = modifier
        ) {
            Box(
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = text,
                    color = Color.Black,
                    style = Typography.h6
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun DarkButton(
    text: String,
    modifier: Modifier = Modifier,
    cornerSize: CornerSize = CornerSize(16.dp),
    onClick: () -> Unit
) {
    Surface(
        selected = false,
        onClick = { onClick() },
        shape = RoundedCornerShape(cornerSize),
        color = DarkButtonBackground,
        modifier = modifier,
    ) {
        Text(
            text = text,
            color = TextPrimary,
            style = Typography.button,
            modifier = Modifier.padding(12.dp)
        )
    }
}