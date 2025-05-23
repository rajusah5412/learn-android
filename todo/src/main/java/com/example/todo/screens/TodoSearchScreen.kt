package com.example.todo.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TodoSearchScreen(modifier: Modifier = Modifier) {
    Surface(color = MaterialTheme.colorScheme.background) {
        Column(modifier.statusBarsPadding()) {
            Row(
                Modifier.fillMaxWidth().padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ){
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back Button")
//        Spacer(Modifier.size(width = 8.dp, height = 0.dp))
                TextField(
                    "",
                    onValueChange = {},
                    placeholder = { Text("Search Todo Here") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {},
                    trailingIcon = {
                        Icon(Icons.Default.Clear, "clear")
                    }
                )
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
private fun TodoSearchScreenPreview() {
    TodoSearchScreen()
}