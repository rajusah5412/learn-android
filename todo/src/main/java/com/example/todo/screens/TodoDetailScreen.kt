package com.example.todo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.todo.MyApplication
import com.example.todo.data.entity.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoDetailScreen(modifier: Modifier = Modifier, id: Int? = null) {

    var todo by remember { mutableStateOf<Todo?>(null) }
    Scaffold(topBar = {
        TopAppBar(title = { Text("Detail") })
    }) {
        Column(
            modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            LaunchedEffect(Unit) {
                launch(Dispatchers.Default) {
                    todo = MyApplication.database.todoDao().findById(id!!)
                }
            }

            if (todo != null) {
                Text(todo!!.title, fontSize = 18.sp)
                Text(
                    todo!!.createDate.toLocalDateTime(TimeZone.of("Asia/Kathmandu")).date.toString(),
                    fontSize = 12.sp
                )
                Text(
                    todo!!.content ?: "",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Justify,
                    lineHeight = 24.sp,
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun TodoDetainPreview() {
    TodoDetailScreen()
}