package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.todo.data.AppDatabase
import com.example.todo.ui.navigation.AppNav
import com.example.todo.ui.theme.GalleryTheme
import com.example.todo.viewmodel.TodoRepository
import com.example.todo.viewmodel.TodoViewModel
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                GalleryTheme {
                    val navHostController = rememberNavController()

                    val ctx = LocalContext.current
                    val vm: TodoViewModel = viewModel<TodoViewModel>(
                        factory = TodoViewModel.Factory,
                        extras = MutableCreationExtras().apply {
                            val todoDao = AppDatabase.getInstance(ctx).todoDao()
                            set(TodoViewModel.REPOSITORY_KEY, TodoRepository(todoDao))
                        })
                    AppNav(navHostController, vm)
                }
            }
        }
    }
}

@Serializable
object TodoList

@Serializable
data class CreateTodo(val id: Int? = null)

@Serializable
data class TodoDetail(val id: Int)


//fun main(){
//    val instant = Clock.System.now()
//    println(instant)
//    val toLocalDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
//    println(toLocalDateTime)
//    println(toLocalDateTime.date)
//    println(toLocalDateTime.time)
//    val indexOfDot = toLocalDateTime.time.toString().indexOf('.')
//    val onlyTime = toLocalDateTime.time.toString().subSequence(0, indexOfDot)
//
//    val lastIndexOfColon = onlyTime.lastIndexOf(':')
//    println(onlyTime.subSequence(0, lastIndexOfColon))
//
//    val hr = toLocalDateTime.time.hour
//    val min = toLocalDateTime.time.minute
//    println("$hr:$min")
//
////    val a = A()
//}
//
//class A private constructor(){
//    fun createAObj(){
//        val a = A()
//    }
//}


//interface MyFactory{
//    fun create()
//}
//
//abstract class AbstractFactory
//
//fun createObj() {
//    val mFactory :  MyFactory = ImplMyFactory()
//    val Factory : MyFactory = object : MyFactory{
//        override fun create() {
//        }
//    }
//
//    val AbstractClassFactory : AbstractFactory = object : AbstractFactory(){
//
//    }
//
//    Factory.create()
//}
//
//class ImplMyFactory : MyFactory{
//    override fun create() {
//
//    }
//}
