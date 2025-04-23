package com.example.todo.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.todo.data.entity.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun TodoAction(
    modifier: Modifier = Modifier,
    todo: Todo,
    isComplete: Boolean,
    updateCompleted: (Boolean) -> Unit = {},
    navigateToUpdate: () -> Unit = {},
    delete: (Todo) -> Unit = {}
) {
    val scope = rememberCoroutineScope()
    val ctx = LocalContext.current
    var offsetX by remember { mutableFloatStateOf(0f) }

    Row(
        modifier = modifier
            .width(100.dp)
            .offset { IntOffset(offsetX.roundToInt(), 0) }
            .draggable(
                state = rememberDraggableState { delta -> offsetX += delta },
                orientation = Orientation.Horizontal,
            ),

        horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(isComplete, onCheckedChange = { completed ->
            scope.launch(Dispatchers.Default) {
                updateCompleted(completed)
            }

        })
        Icon(
            imageVector = Icons.Default.Edit,
            "edit",
            tint = Color.Green,
            modifier = Modifier.clickable { navigateToUpdate() })
        Icon(
            imageVector = Icons.Default.Delete,
            "delete",
            tint = Color.Red,
            modifier = Modifier.clickable {
                delete(todo)
            })
    }
}