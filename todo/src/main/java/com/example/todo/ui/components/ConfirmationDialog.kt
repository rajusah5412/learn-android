package com.example.todo.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ConfirmationDialog(
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    positiveAction: () -> Unit,
    negativeAction : ()-> Unit,
    onDismiss: () -> Unit,

) {
    AlertDialog(
        title = { Text(title) },
        text = { Text(text) },
        confirmButton = {
            Button(onClick = {
                positiveAction()
//                vm.deleteTodo(vm.todoToEdit!!.id)
//                showConfirmationDialog = false
            }) {
                Text("Yes")
            }
        },
        dismissButton = {
            Button(onClick = {
                negativeAction()
//                showConfirmationDialog = false
            }) {
                Text("No")
            }
        },
        onDismissRequest = {
            onDismiss()
//            showConfirmationDialog = false
        })
}