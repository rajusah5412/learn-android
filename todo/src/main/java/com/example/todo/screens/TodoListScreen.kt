package com.example.todo.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todo.data.entity.Todo
import com.example.todo.ui.components.ConfirmationDialog
import com.example.todo.ui.components.TodoActionBar
import com.example.todo.ui.components.TodoItem
import com.example.todo.ui.components.TodoSearchAppBar
import com.example.todo.viewmodel.TodoViewModel
import kotlinx.coroutines.launch

//@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    vm: TodoViewModel,
    navigateToDetail: (Int) -> Unit = {},
    navigateToCreate: (Todo?) -> Unit = {},
    navigateToSearch: () -> Unit = {}
) {

    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Drawer title", modifier = Modifier.padding(16.dp))
                HorizontalDivider()
                NavigationDrawerItem(
                    label = { Text(text = "Drawer Item") },
                    selected = false,
                    onClick = { /*TODO*/ })
                NavigationDrawerItem(
                    label = { Text(text = "Drawer Item") },
                    selected = false,
                    onClick = { /*TODO*/ })
                NavigationDrawerItem(
                    label = { Text(text = "Drawer Item") },
                    selected = false,
                    onClick = { /*TODO*/ })
                NavigationDrawerItem(
                    label = { Text(text = "Settings") },
                    selected = false,
                    onClick = { /*TODO*/ })
            }
        },
    ) {

        var showActionBar by rememberSaveable { mutableStateOf(false) }

        var showConfirmationDialog by rememberSaveable { mutableStateOf(false) }
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            if (!showActionBar) {
                TodoSearchAppBar(
                    navigateToSearch = navigateToSearch,
                    modifier = Modifier.statusBarsPadding(),
                    leadingAction = {
                        scope.launch {
                            if (drawerState.isOpen) {
                                drawerState.close()
                            } else {
                                drawerState.open()
                            }
                        }
                    })
            } else {
                TodoActionBar(
                    selectedTodos = vm.todoToEdit.toList(),
                    modifier = Modifier.fillMaxWidth(),
                    onClear = {
                        vm.clearSelectedTodos()
                        showActionBar = false
                    },
                    onDelete = { showConfirmationDialog = true }

                )
            }

        }, floatingActionButton = {
            FloatingActionButton(onClick = {
                vm.clearSelectedTodos()
                showActionBar = false
                navigateToCreate(null)
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add")
            }
        }) {
            if (showConfirmationDialog) {
                ConfirmationDialog(
                    title = "Delete?",
                    text = "Are you sure you want to delete this todo",
                    positiveAction = {
                        vm.todoToEdit.toList().forEach {
                            vm.deleteTodo(it.id)
                        }
                        vm.clearSelectedTodos()
                        showActionBar = false
                        showConfirmationDialog = false
                    },
                    negativeAction = {
                        showConfirmationDialog = false
                    },
                    onDismiss = {
                        showConfirmationDialog = false
                    })
            }
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(8.dp),
                verticalItemSpacing = 6.dp,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {


                items(vm.todos) { todo ->

                    TodoItem(
                        todo = todo,
                        showBorder = vm.todoToEdit.toList().contains(todo),
                        onItemClick = {
                            if (vm.todoToEdit.isNotEmpty()) {
                                if (vm.todoToEdit.toList().contains(todo)) {
                                    vm.removeSelectedTod(todo)
                                } else {
                                    vm.updateSelectedTodo(todo)
                                }

                            } else {
                                navigateToDetail(it)
                            }
                        },
                        toggleActionBar = { todo ->
                            vm.updateSelectedTodo(todo)
                            showActionBar = true
                        })
                }
            }


//                TodoAction(todo = todo, isComplete = todo.isCompleted, updateCompleted = {
//                    vm.markCompleted(todo)
//                }, navigateToUpdate = { navigateToCreate(todo) }, delete = vm::deleteTodo)
        }

    }
}