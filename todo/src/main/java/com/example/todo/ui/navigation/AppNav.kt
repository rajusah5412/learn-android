package com.example.todo.ui.navigation

import CompletedTaskScreen
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import com.example.todo.CompletedTask
import com.example.todo.CreateTodo
import com.example.todo.SearchBar
import com.example.todo.TodoDetail
import com.example.todo.TodoList
import com.example.todo.data.AppDatabase
import com.example.todo.screens.CreateTodoScreen
import com.example.todo.screens.TodoDetailScreen
import com.example.todo.screens.TodoListScreen
import com.example.todo.screens.TodoSearchScreen
import com.example.todo.viewmodel.TodoRepository
import com.example.todo.viewmodel.TodoViewModel

@Composable
fun AppNav(navController: NavHostController, viewModel: TodoViewModel) {
    NavHost(
        navController = navController,
        startDestination = TodoList
    ) {
        composable<TodoList> {

            TodoListScreen(
                viewModel,
                navigateToDetail = { todoId ->
                    navController.navigate(TodoDetail(todoId))
                }, navigateToCreate = {
                    navController.navigate(CreateTodo(it?.id))
                },
                navigateToSearch = {
                    navController.navigate(SearchBar, navOptions {
                        popUpTo<TodoList>() {
                            inclusive = true
                        }
                    })
                },
                navigateToCompleted = {
                    //
                    navController.navigate(CompletedTask)
                }
            )
        }
        composable<CompletedTask> {
            CompletedTaskScreen(
                viewModel,
                navigateToDetail = { todoId ->
                    navController.navigate(TodoDetail(todoId))
                }, navigateToCreate = {
                    navController.navigate(CreateTodo(it?.id))
                },
                navigateToSearch = {
                    navController.navigate(SearchBar, navOptions {
                        popUpTo<TodoList>() {
                            inclusive = true
                        }
                    })
                }
            )
        }
        composable<CreateTodo> { navBackStackEntry ->
            val id = navBackStackEntry.toRoute<CreateTodo>().id
            if (id != null)
                viewModel.getById(id)
            val todo = viewModel.todo.collectAsStateWithLifecycle()
            CreateTodoScreen(
                createTodo = viewModel::createOrUpdate,
                todo = todo.value,
                navigateToList = {
                    navController.navigate(TodoList, navOptions = navOptions {
                        popUpTo<TodoList>() {
                            inclusive = true
                        }
                    })
                },
                clear = {
                    viewModel.clearSelectedTodos()
                })
        }
        composable<TodoDetail> { entry ->
            val id = entry.toRoute<TodoDetail>().id
            TodoDetailScreen(id = id)
        }

        composable<SearchBar> {
            TodoSearchScreen(modifier = Modifier.fillMaxSize())
        }
    }
}

interface IDownload{
    fun download()
}

class MyDownload : IDownload{
    override fun download(){

    }
}

class MySecondDownload : IDownload{
    override fun download() {
        TODO("Not yet implemented")
    }

}

