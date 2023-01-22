package com.example.app_semi_final.util

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntSize

val LocalDragTargetInfo = compositionLocalOf { DragTargetInfo() }

class DragTargetInfo {
    var isDragging by mutableStateOf(false)
    var dragPosition by mutableStateOf(Offset.Zero)
    var dragOffset by mutableStateOf(Offset.Zero)
    var draggableComposable by mutableStateOf<(@Composable () -> Unit)?>(null)
    var dataToDrop by mutableStateOf<Any?>(null)
}

@Composable
fun DraggingBox(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit,
) {
    val dragState = remember { DragTargetInfo() }
    CompositionLocalProvider(
        LocalDragTargetInfo provides dragState
    ) {
        Box(
            modifier = modifier.fillMaxSize()
        ) {
            content()
            if (dragState.isDragging) {
                var targetSize by remember { mutableStateOf(IntSize.Zero) }
                Box(
                    modifier = Modifier
                        .onGloballyPositioned { coordinates ->
                            targetSize = coordinates.size
                        }
                        .graphicsLayer {
                            val offset = (dragState.dragPosition + dragState.dragOffset)
                            scaleX = 1.3f
                            scaleY = 1.3f
                            alpha = if (targetSize == IntSize.Zero) 0f else .9f
                            translationX = offset.x.minus(targetSize.width / 2)
                            translationY = offset.y.minus(targetSize.height / 2)
                        }
                ) {
                    dragState.draggableComposable?.invoke()
                }
            }
        }
    }
}

@Composable
fun <T> Draggable(
    modifier: Modifier = Modifier,
    dataToDrop: T,
    startDragging: () -> Unit = {},
    stopDragging: () -> Unit = {},
    content: @Composable (() -> Unit),
) {
    var currentPosition by remember { mutableStateOf(Offset.Zero) }
    val currentState = LocalDragTargetInfo.current

    Box(
        modifier = modifier
            .onGloballyPositioned { coordinates ->
                currentPosition = coordinates.localToWindow(Offset.Zero)
            }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragStart = { offset ->
                        startDragging()
                        currentState.dataToDrop = dataToDrop
                        currentState.isDragging = true
                        currentState.dragPosition = currentPosition + offset
                        currentState.draggableComposable = content
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        currentState.dragOffset += Offset(dragAmount.x, dragAmount.y)
                    },
                    onDragEnd = {
                        stopDragging()
                        currentState.isDragging = false
                        currentState.dragOffset = Offset.Zero
                    },
                    onDragCancel = {
                        stopDragging()
                        currentState.dragOffset = Offset.Zero
                        currentState.isDragging = false
                    }
                )
            }
    ) {
        content()
    }
}

@Composable
fun <T> DraggableReceiver(
    modifier: Modifier = Modifier,
    content: @Composable (BoxScope.(isDraggableWithinBounds: Boolean, data: T?) -> Unit),
) {
    val dragInfo = LocalDragTargetInfo.current
    val dragPosition = dragInfo.dragPosition
    val dragOffset = dragInfo.dragOffset
    var isDraggableWithinReceiverBounds by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .onGloballyPositioned {
                it.boundsInWindow().let { rect ->
                    isDraggableWithinReceiverBounds = rect.contains(dragPosition + dragOffset)
                }
            }
    ) {
        val data = if (isDraggableWithinReceiverBounds && !dragInfo.isDragging) {
            dragInfo.dataToDrop as? T
        } else {
            null
        }

        content(isDraggableWithinReceiverBounds, data)
    }
}
