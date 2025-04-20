package com.example.todo.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TodoSearchAppBar(modifier: Modifier = Modifier, navigateToSearch: () -> Unit = {}) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.Menu, contentDescription = "menu")
        TextField(
            "",
            onValueChange = {},
            placeholder = { Text("Search Todo Here") },
            modifier = Modifier.fillMaxWidth().clickable{
                navigateToSearch()
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TodoSearchAppBarPreview() {
    TodoSearchAppBar()
}