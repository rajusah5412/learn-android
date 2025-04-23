package com.example.todo.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.data.entity.Todo
import com.example.todo.ui.theme.Inter
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.roundToInt

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoItem(
    modifier: Modifier = Modifier,
    todo: Todo,
    showBorder: Boolean = false,
    onItemClick: (Int) -> Unit,
    toggleActionBar: (Todo) -> Unit
) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    Card(
        border = if (showBorder) BorderStroke(4.dp, Color.Green) else null,
        modifier = modifier
            .combinedClickable(
                onClick = {
//                    if(!showBorder)
                        onItemClick(todo.id)
                },
                onLongClick = {
                    toggleActionBar(todo)
                },
                onDoubleClick = {},
            )
            .offset { IntOffset(offsetX.roundToInt(), 0) }
            .draggable(
                state = rememberDraggableState { delta -> offsetX += delta },
                orientation = Orientation.Horizontal,
            )
    ) {
        Column(modifier = Modifier.padding(6.dp)) {

            Text(
                todo.title,
                maxLines = 1, fontSize = 16.sp,
//            fontStyle = FontStyle.Italic,
                fontFamily = Inter,
                fontWeight = FontWeight.Bold,
                color = if (todo.isCompleted) Color.Red else MaterialTheme.colorScheme.contentColorFor(
                    MaterialTheme.colorScheme.background
                ),
//            textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else TextDecoration.Underline
            )
            Text(
                todo.createDate.toLocalDateTime(timeZone = TimeZone.of("Asia/Kathmandu")).date.toString(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )

            if (todo.content != null) {
                Text(
                    text = todo.content,
                    color = if (todo.isCompleted) Color.Red else MaterialTheme.colorScheme.contentColorFor(
                        MaterialTheme.colorScheme.background
                    ),
                    fontFamily = Inter,
                    maxLines = 10,
                    overflow = TextOverflow.Ellipsis,
                    lineHeight = 22.sp,
                    textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else TextDecoration.None
                )
            }
        }
    }
}