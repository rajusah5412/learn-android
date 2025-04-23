package com.example.todo.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.todo.data.entity.Todo

typealias ClickLambda = () -> Unit
// we have selected todo items in this function
@Composable
fun TodoActionBar(
    modifier: Modifier = Modifier,
    selectedTodos: List<Todo?> = emptyList(),
    onClear: ClickLambda = {},
    onEdit: (Todo) -> Unit = {},
    onDelete: (id: Int) -> Unit = {},
    onShare: ClickLambda = {},
    onComplete: ClickLambda = {}

) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onClear) {
                Icon(imageVector = Icons.Default.Clear, "clear")
            }
            Text(selectedTodos.size.toString())
        }
        Row {
            if(selectedTodos.size == 1){
                IconButton(onClick = {
//                onEdit(todo!!)
                }) {
                    Icon(imageVector = Icons.Default.Edit, "edit")

                }
            }



            IconButton(onClick = onShare) {
                Icon(imageVector = Icons.Default.Share, "share")
            }
            IconButton(onClick = {
                onDelete(0)
            })
            {
                Icon(imageVector = Icons.Default.Delete, "delete")

            }
            IconButton(onClick = onComplete) {
                Icon(imageVector = Icons.Default.Check, "check")

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TodoActionBarPreview() {
    TodoActionBar()
}