package com.example.todo.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.todo.CreateTodo
import com.example.todo.data.entity.Todo
import com.example.todo.ui.components.TodoSearchAppBar
import com.example.todo.ui.theme.Inter
import com.example.todo.ui.theme.PoetSenOne
import com.example.todo.viewmodel.TodoViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.roundToInt

//@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    vm: TodoViewModel, navigateToDetail: (Int) -> Unit = {}, navigateToCreate: (Todo?) -> Unit = {}
) {

    Scaffold(
        topBar = {
            TodoSearchAppBar()
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navigateToCreate(null)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) {
        var showActionBar by remember { mutableStateOf(false) }
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalItemSpacing = 6.dp,
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {

            items(vm.todos) { todo ->

                TodoItem(todo = todo, onItemClick = navigateToDetail, toggleActionBar = {
                    showActionBar = true
                })
//                TodoAction(todo = todo, isComplete = todo.isCompleted, updateCompleted = {
//                    vm.markCompleted(todo)
//                }, navigateToUpdate = { navigateToCreate(todo) }, delete = vm::deleteTodo)
            }

        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TodoItem(modifier: Modifier = Modifier, todo: Todo, onItemClick: (Int) -> Unit, toggleActionBar: () -> Unit) {
    var offsetX by remember { mutableFloatStateOf(0f) }
    Card(
        modifier = modifier
            .combinedClickable(
                onClick = {
                    onItemClick(todo.id)
                },
                onLongClick = {
                    toggleActionBar()
                },
                onDoubleClick = {},
            )
            .offset { IntOffset(offsetX.roundToInt(), 0) }
            .draggable(
                state = rememberDraggableState { delta -> offsetX += delta },
                orientation = Orientation.Horizontal,
            )) {
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
                fontSize = 12.sp, fontWeight = FontWeight.Medium
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
            imageVector = Icons.Default.Edit, "edit", tint = Color.Green,
            modifier = Modifier.clickable { navigateToUpdate() }
        )
        Icon(
            imageVector = Icons.Default.Delete,
            "delete",
            tint = Color.Red,
            modifier = Modifier.clickable {
                delete(todo)
            })
    }
}

@Preview(showBackground = true)
@Composable
private fun BasicText() {
    BasicTextField("hello", onValueChange = {}, decorationBox = { it -> Row(modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)) { it() }})
}
