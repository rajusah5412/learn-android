package com.example.todo.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras

import com.example.todo.data.dao.TodoDao
import com.example.todo.data.entity.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MyViewModel(private val repository: TodoRepository) : ViewModel(){

    val todos = mutableStateListOf<Todo>()

    init {
        getAllTodos()
    }


    companion object{
        val REPOSITORY_KEY = object : CreationExtras.Key<TodoRepository>{}
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val repo = checkNotNull(extras[REPOSITORY_KEY])
                val obj =  MyViewModel(repo) as T
                return obj
            }
        }
    }

    fun getAllTodos() {
       viewModelScope.launch(Dispatchers.IO) {
           val allTodos = repository.getAllTodos()
           withContext(Dispatchers.Main) {
               todos.addAll(allTodos)
           }
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
