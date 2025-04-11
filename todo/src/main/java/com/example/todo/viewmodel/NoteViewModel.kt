package com.example.todo.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.MyApplication
import com.example.todo.data.AppDatabase
import com.example.todo.data.dao.TodoDao
import com.example.todo.data.entity.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyViewModel(val app: MyApplication) : AndroidViewModel(app){
    private val  repository : TodoRepository = TodoRepository(
        AppDatabase.getInstance(this@MyViewModel.getApplication()).userDao()
    )
    val todos = mutableStateListOf<Todo>()

    init {
        getAllTodos()
    }

    fun getAllTodos() {
       viewModelScope.launch(Dispatchers.IO) {
           todos.addAll(repository.getAllTodos())
       }
    }

}
class TodoRepository(private val todoDao: TodoDao){
    suspend fun getAllTodos(): List<Todo> {
        return todoDao.getAll()
    }
    suspend fun saveOrUpdate(todo: Todo){
        todoDao.upsert(todo)
    }
    suspend fun delete(todo: Todo){
        todoDao.delete(todo)
    }
}
