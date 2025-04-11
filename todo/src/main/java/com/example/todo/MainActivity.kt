package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.todo.screens.CreateTodoScreen
import com.example.todo.screens.TodoDetailScreen
import com.example.todo.screens.TodoListScreen
import com.example.todo.ui.navigation.AppNav
import com.example.todo.ui.theme.GalleryTheme
import com.example.todo.viewmodel.MyViewModel
import com.example.todo.viewmodel.TodoRepository
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
//    val vm : MyViewModel by viewModels()
//    lateinit var viewModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val extras = MutableCreationExtras()

        extras[MyViewModel.REPOSITORY_KEY] = TodoRepository(MyApplication.database.userDao())
//        viewModel = ViewModelProvider.create(  owner = this@MainActivity, factory =  MyViewModel.Factory,  extras = extras)[MyViewModel::class.java]
        enableEdgeToEdge()
        setContent {
            Surface {
                GalleryTheme {

                    val viewModel : MyViewModel = viewModel( factory = MyViewModel.Factory, extras = extras)

                    val navController = rememberNavController()
                    AppNav(navController)
                }
            }
        }
    }
}

@Serializable
object TodoList

@Serializable
object CreateTodo

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

val x =  { a: Int ->  println(a)}

val y = fun (a: Int) {

}

fun add(a: (Int) -> Unit){
    a(10)
}

fun main() {
    add(x)
}

interface MyFactory{
    fun create()
}

abstract class AbstractFactory

fun createObj() {
    val mFactory :  MyFactory = ImplMyFactory()
    val Factory : MyFactory = object : MyFactory{
        override fun create() {
        }
    }

    val AbstractClassFactory : AbstractFactory = object : AbstractFactory(){

    }

    Factory.create()
}

class ImplMyFactory : MyFactory{
    override fun create() {

    }
}
