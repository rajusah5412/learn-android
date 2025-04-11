package com.example.storage

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.storage.ui.theme.GalleryTheme
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GalleryTheme {
                val navController = rememberNavController()
                NavHost(
                        navController = navController,
                        startDestination = Login
                    ) {
//                        composable(route = "Login") {
//                            LoginScreen{
//                                navController.navigate("Home/")
//                            }
//                        }
                        composable<Login> {
                            LoginScreen {
                                navController.navigate(Home("uid012"))
                            }
                        }

//                        composable(route = "Home/{userId}") { navBackStackEntry ->
//                            val userId = navBackStackEntry.arguments?.getString("userId") ?: "Invalid User Id"
//                            HomeScreen(userId = userId){
//                                navController.navigate("Details")
//                            }
//                        }
                        composable<Home> { navBackStackEntry ->
                            val userId = navBackStackEntry.toRoute<Home>().id
                            HomeScreen(userId = userId) {
                                navController.navigate(Profile(userId))
                            }
                        }
                        composable<Profile> { entry ->
                            val userId = entry.toRoute<Profile>().id
                            ProfileScreen(id = userId)
                        }
                    }

            }
        }
    }
}


@Composable
fun LoginScreen(modifier: Modifier = Modifier, onLogin: () -> Unit) {
    Column {
        Text("Login Screen")
        Button(onClick = onLogin) {
            Text("Login")
        }
    }
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier, userId: String, onNavigate: () -> Unit) {
    Column {
        Text("Home Screen")
        Button(onClick = onNavigate) {
            Text("Go to Details")
        }
        Text(userId)
    }
}

@Composable
fun ProfileScreen(modifier: Modifier = Modifier, id: String) {
    Column {
        Text("Detail Screen")
        Button(onClick = {}) {
            Text("")
        }
        Text(id)
    }
}

@Serializable
object Login;
@Serializable
data class Home(val id: String)

@Serializable
data class Profile(val id : String)

@Serializable
data object Register


fun main() {

    listOf(Book())
    add("hello", "")
    val myList = MyList<Int>()
    myList.add(1)
    myList.add(2)
    myList.printListItems()
}

class Book

// kotlin generic function with two type parameters
 fun <T, U> add(t : T, u : U) : U{
     return u
 }

class MyList<T>{
    val list: MutableList<T> = mutableListOf()

    fun  add(item: T){
        list.add(item)
    }

    fun remove(index: Int){
        list.removeAt(index)
    }

    fun printListItems(){
        println(list)
    }
}


