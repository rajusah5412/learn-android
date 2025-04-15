package com.example.todo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
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

@Composable
fun TodoDetailScreen(modifier: Modifier = Modifier, id: Int? = null) {

    var todo by remember { mutableStateOf<Todo?>(null) }
    Column(
        modifier.fillMaxSize(),


        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        LaunchedEffect(Unit) {
           launch (Dispatchers.Default){
               todo = MyApplication.database.todoDao().findById(id!!)
           }
        }

        if (todo != null) {
            Text(todo!!.title, fontSize = 25.sp)
            Text(todo!!.createDate.toString(), fontSize = 10.sp)
            Text(
                todo!!.content ?: "",
                fontSize = 20.sp,
                textAlign = TextAlign.Justify,
                modifier = Modifier.background(color = Color.Magenta)
            )

        }
    }

}


@Preview(showBackground = true)
@Composable
private fun TodoDetainPreview() {
    TodoDetailScreen()
}