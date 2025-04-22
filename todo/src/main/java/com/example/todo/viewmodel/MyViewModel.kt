package com.example.todo.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todo.data.dao.TodoDao
import com.example.todo.data.entity.Todo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TodoViewModel(val greeting: String, val repo: TodoRepository) : ViewModel() {
    var todoToEdit by  mutableStateOf<Todo?>(null)
       private set
    val todos = mutableStateListOf<Todo>()
    private val _todo = MutableStateFlow<Todo?>(null)
    val todo: StateFlow<Todo?> = _todo


    init {
        getAllTodos()
    }

    fun updateSelectedTodo(selectTodo: Todo){
        todoToEdit = selectTodo
    }

    fun createOrUpdate(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.saveOrUpdate(todo)
            getAllTodos()
        }
    }

    fun resetTodo(){
        _todo.value = null
    }

    fun getById(id: Int){
        viewModelScope.launch(Dispatchers.Default) {
            val result = repo.getSingleTodo(id)
            _todo.value = result
        }
    }

    fun deleteTodo(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.delete(id)
            getAllTodos()
        }
    }

    fun markCompleted(todo: Todo) {
        val toUpdate = todo.copy(isCompleted = !todo.isCompleted)
        viewModelScope.launch(Dispatchers.IO) {
            repo.saveOrUpdate(toUpdate)
            getAllTodos()
        }
    }

    companion object {
        val REPOSITORY_KEY = object : CreationExtras.Key<TodoRepository> {}
        val Factory = viewModelFactory {
            initializer {
                val repo = checkNotNull(this[REPOSITORY_KEY])
                TodoViewModel("hello", repo)
            }
        }
    }

    fun getAllTodos() {
        viewModelScope.launch(Dispatchers.IO) {
            val allTodos = repo.getAllTodos().sortedByDescending { it.id }
            withContext(Dispatchers.Main) {
                todos.clear()
                todos.addAll(allTodos)
            }
        }
    }
}

class TodoRepository(private val todoDao: TodoDao) {
    suspend fun getAllTodos(): List<Todo> {
        return todoDao.getAll()
    }

    suspend fun getSingleTodo(id: Int): Todo {
        return todoDao.findById(id)
    }

    suspend fun saveOrUpdate(todo: Todo) {
        todoDao.upsert(todo)
    }

    suspend fun delete(id: Int) {
        todoDao.delete(id)
    }
}
