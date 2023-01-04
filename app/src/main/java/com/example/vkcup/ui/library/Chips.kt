
package com.example.vkcup.ui.library

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.example.vkcup.R
import com.example.vkcup.ui.theme.*

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun Chip(
    isActive: Boolean,
    chipText: String,
    onToggle: () -> Unit
) {
    val chipBackgroundColor by animateColorAsState(if (isActive) Accent else ChipBackground)
    val dividerColor by animateColorAsState(if (isActive) Accent else DividerColor)
    Surface(
        selected = false,
        onClick = { onToggle() },1
        shape = Shapes.large,
        color = chipBackgroundColor,
        modifier = Modifier.height(IntrinsicSize.Max)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(12.dp)
        ) {
            Text(text = chipText, style = Typography.button, color = TextPrimary)

            Spacer(modifier = Modifier.width(14.dp))

            Divider(
                color = dividerColor,
                modifier = Modifier
                    .fillMaxHeight()
                    .width(1.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            if (!isActive) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_plus),
                    contentDescription = stringResource(R.string.include_topic),
                    tint = Color.White
                )
            } else {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_check),
                    contentDescription = stringResource(R.string.exclude_topic),
                    tint = Color.White
                )
            }
        }
    }
}