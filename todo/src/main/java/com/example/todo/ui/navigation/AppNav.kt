package com.example.todo.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.todo.CreateTodo
import com.example.todo.TodoDetail
import com.example.todo.TodoList
import com.example.todo.screens.CreateTodoScreen
import com.example.todo.screens.TodoDetailScreen
import com.example.todo.screens.TodoListScreen

@Composable
fun AppNav(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = TodoList
    ) {
        composable<TodoList> {
            TodoListScreen(navigateToDetail = { todoId ->
                navController.navigate(TodoDetail(todoId))
            }, navigateToCreate = {
                navController.navigate(CreateTodo)
            })
        }
        composable<CreateTodo> { navBackStackEntry ->
            CreateTodoScreen {
                navController.navigate(TodoList)
            }
        }
        composable<TodoDetail> { entry ->
            val id = entry.toRoute<TodoDetail>().id
            TodoDetailScreen(id = id)
        }
    }
}