package com.example.todo.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todo.data.entity.Todo
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun CreateTodoScreen(
    todo: Todo? = null,
    createTodo: (Todo) -> Unit = {},
    navigateToList: () -> Unit = {},
) {

    val scope = rememberCoroutineScope()

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    LaunchedEffect(todo) {
        if(todo != null){
            title = todo.title
            content = todo.content ?: ""
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(if(todo != null) "Edit Todo" else  "Create Todo", fontSize = 24.sp)
        Spacer(Modifier.size(30.dp))
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("title") })
        Spacer(Modifier.size(20.dp))
        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Content") },
            maxLines = 10,
            minLines = 10
        )
        Spacer(Modifier.size(50.dp))
        OutlinedButton(onClick = {
            scope.launch {
                val save =
                    todo?.copy(title = title, content = content) ?: Todo(title = title, content = content)
                createTodo(save)
                navigateToList()
            }
        }) {
            Text(if (todo != null) "Update" else "Create")
        }


//        DatePickerModalInput(onDateSelected = {}, onDismiss = {})
    }
}