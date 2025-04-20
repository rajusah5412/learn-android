package com.example.todo.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SearchField(
    modifier: Modifier = Modifier,
    value: String,
    shape: Shape? = null,
    readOnly: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    onValueChanged: (String) -> Unit,
    trailingIcon: (@Composable () -> Unit)? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    placeHolder: (@Composable () -> Unit)? = null
) {
    BasicTextField(
        modifier = modifier
            .clip(shape = RoundedCornerShape(40.dp))
            .background(Color.Red),
        value = value,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = TextStyle.Default.copy(fontSize = 16.sp),
        onValueChange = onValueChanged,
        singleLine = true,
        decorationBox = { innerTextField ->

            Card(
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(100.dp)),
                elevation = CardDefaults.cardElevation(defaultElevation = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    leadingIcon?.invoke()

                    Box(
                        Modifier
                            .weight(1f)
                            .padding(vertical = 16.dp)
                            .clickable(onClick = onClick)
                    ) {
                        if (placeHolder != null) {
                            placeHolder()
                        } else {
                            innerTextField()
                        }
                    }

                    trailingIcon?.invoke()

                }
            }
        })
}

@Preview(showBackground = true)
@Composable
fun SearchFieldPreview() {
    var value by remember { mutableStateOf("") }
    SearchField(Modifier.fillMaxWidth(), leadingIcon = {
        Icon(Icons.Default.Add, "", modifier = Modifier.padding(horizontal = 8.dp))
    }, trailingIcon = {
        Icon(Icons.Default.Menu, "", modifier = Modifier.padding(horizontal = 8.dp))
    }, value = value, onValueChanged = {
        value = it
    })
}