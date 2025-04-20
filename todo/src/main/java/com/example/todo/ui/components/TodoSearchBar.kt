package com.example.todo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TodoSearchAppBar(
    modifier: Modifier = Modifier,
    leadingAction: () -> Unit,
    navigateToSearch: () -> Unit = {}
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var value by remember { mutableStateOf("") }
//        Icon(Icons.Default.Menu, contentDescription = "menu")
        val stroke = remember(1f) {
            Stroke(width = 1f)
        }
        SearchField(
            enabled = false,
            readOnly = true,
            onClick = {navigateToSearch()},
            value = value, onValueChanged = { value = it },
            leadingIcon = {
                IconButton(onClick = leadingAction) {
                    Icon(Icons.Default.Menu, "menu")
                }
            },
            trailingIcon = {
                IconButton(onClick = {}) {
                    Box(
                        modifier = Modifier
                            .drawWithContent {
                                drawContent()
                                drawCircle(
                                    Color.Blue,
                                    size.minDimension / 2,
                                    size.center,
                                    style = Fill
                                )
                            }
                            .clip(CircleShape)
                            .size(30.dp)
                    )
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TodoSearchAppBarPreview() {
    TodoSearchAppBar(leadingAction = {})
}