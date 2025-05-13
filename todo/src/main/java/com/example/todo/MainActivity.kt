package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotApplyResult
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.todo.data.AppDatabase
import com.example.todo.ui.navigation.AppNav
import com.example.todo.ui.theme.TodoTheme
import com.example.todo.viewmodel.TodoRepository
import com.example.todo.viewmodel.TodoViewModel
import kotlinx.coroutines.delay
import kotlinx.serialization.Serializable

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
//        enableEdgeToEdge()
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)
        setContent {
            TodoTheme {
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

@Serializable
object TodoList

@Serializable
data class CreateTodo(val id: Int? = null)

@Serializable
data class TodoDetail(val id: Int)
@Serializable
object SearchBar

@Serializable
object CompletedTask


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

class Fish
class Ocean {
    val fish: Fish? = null
}

@Composable
fun Test(modifier: Modifier = Modifier) {
    val x: Int by remember { mutableStateOf(8) }
}

@JvmInline
value class Password(val value: String)

@JvmInline
value  class Email(val value: String){
    init {

    }
}

sealed interface X

class Loading : X
data class Success<T>(val data: T) : X
data class Failure(val throwable: Throwable? = null) : X


class User(val userName: String , val password: Password, val email : Email)


suspend fun main() {

    var getData : X = Loading()

    delay(2000)

    getData = Success<String>("file downloaded")

    /// after 10 seconds
    getData = Success<String>("hello")
    when(getData){
        is Loading -> "loading"
        is Success<String> -> "Succces"
        is Failure -> "failure"
    }
    val user = User("jack", Password("hello"), Email("acc@gmail.com"))
}



