package com.example.app_semi_final.ui.library

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.app_semi_final.ui.theme.Accent
import com.example.app_semi_final.ui.theme.OnAccent
import com.example.app_semi_final.ui.theme.Typography


@Composable
@OptIn(ExperimentalMaterialApi::class)
fun AccentButton(
    text: String,
    cornerSize: CornerSize = CornerSize(8.dp),
    onClick: () -> Unit
) {
    Surface(
        onClick = onClick,
        shape = RoundedCornerShape(cornerSize),
        color = Accent,
    ) {
        Text(
            text = text,
            color = OnAccent,
            style = Typography.body1.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        )
    }
}