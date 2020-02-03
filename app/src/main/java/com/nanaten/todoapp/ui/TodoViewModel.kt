package com.nanaten.todoapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanaten.todoapp.database.Todo
import com.nanaten.todoapp.domain.TodoRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class TodoViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {
    val todoList = MutableLiveData<MutableList<Todo>>(mutableListOf())

    fun getTodoList() {
        viewModelScope.launch {
            try {
                val todos = repository.getTodoList()
                todos.collect {
                    todoList.postValue(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addTodo(title: String = "") {
        val lastId = todoList.value?.lastOrNull()?.id ?: 0
        val entity = Todo(lastId + 1, title, false)
        val todos = todoList.value
        todos?.add(entity)
        todoList.postValue(todos)
        repository.addTodo(todoList.value?.toList() ?: emptyList())
    }
}
