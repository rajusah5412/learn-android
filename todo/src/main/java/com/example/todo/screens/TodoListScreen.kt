package com.example.todo.screens

import android.R.attr.title
import android.app.Application
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todo.MyApplication
import com.example.todo.data.AppDatabase
import com.example.todo.data.entity.Todo
import com.example.todo.viewmodel.MyViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(vm: MyViewModel = viewModel<MyViewModel>(), navigateToDetail: (Int)-> Unit= {}, navigateToCreate: ()-> Unit= {}) {
    Box(contentAlignment = Alignment.BottomEnd) {
        Column(Modifier.fillMaxSize()) {
            TopAppBar(title = { Text("Todo List") })

            LazyColumn {
                items(vm.todos) { todo ->
                    var isComplete by remember { mutableStateOf(todo.isCompleted) }
                    Row(
                        Modifier
                            .padding(5.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        TodoItem(todo = todo, onItemClick = navigateToDetail)
                        TodoAction(todo = todo, isComplete = isComplete, updateCompleted = {
                            isComplete = it
                        })
                        Spacer(Modifier.size(8.dp))
                    }
                }
            }
        }
        FloatingActionButton(onClick = navigateToCreate) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Add",)
        }
    }
}


@Composable
fun RowScope.TodoItem(modifier: Modifier = Modifier, todo: Todo, onItemClick: (Int) -> Unit) {
    Column(Modifier.weight(.6f).clickable{
        onItemClick(todo.id)
    }) {
        Text(
            todo.title,
            maxLines = 1, fontSize = 18.sp,
//                            color = Color.Blue,
//                            fontWeight = FontWeight.Black,
            color =  if(todo.isCompleted) Color.Red else Color.Black ,
            textDecoration =  if(todo.isCompleted) TextDecoration.LineThrough else  TextDecoration.Underline
        )
        if (todo.content != null) {
            Text(
                text = todo.content,
                color =  if(todo.isCompleted) Color.Red else Color.Black ,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 24.sp,
                textDecoration =  if(todo.isCompleted) TextDecoration.LineThrough else TextDecoration.None
            )
        }
    }
}

@Composable
fun TodoAction(modifier: Modifier = Modifier, todo: Todo, isComplete: Boolean, updateCompleted: (Boolean) -> Unit = {}) {
    val scope = rememberCoroutineScope()
    val ctx = LocalContext.current
    Row(
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(isComplete, onCheckedChange = { completed ->
            scope.launch(Dispatchers.Default) {
                updateCompleted(completed)
                AppDatabase.getInstance(ctx).userDao().upsert(
                    Todo(
                        id = todo.id,
                        title = todo.title,
                        content = todo.content,
                        isCompleted = completed,
                        createDate = todo.createDate
                    )
                )
//                updateTodo()
            }

        })
        Icon(imageVector = Icons.Default.Edit, "edit", tint = Color.Green)
        Icon(imageVector = Icons.Default.Delete, "delete", tint = Color.Red, modifier = Modifier.clickable{
            scope.launch(Dispatchers.IO) {
                AppDatabase.getInstance(ctx).userDao().delete(todo)
//                updateTodo()
            }
        })
    }
}

