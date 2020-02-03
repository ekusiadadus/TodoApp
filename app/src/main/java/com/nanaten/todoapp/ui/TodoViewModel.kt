package com.nanaten.todoapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanaten.todoapp.database.Todo
import com.nanaten.todoapp.domain.TodoRepository
import com.nanaten.todoapp.util.combine
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class TodoViewModel @Inject constructor(private val repository: TodoRepository) : ViewModel() {
    val todoListAll = MutableLiveData<MutableList<Todo>>(mutableListOf())
    val state = MutableLiveData<TodoState>(TodoState.ACTIVE)
    val todoList: LiveData<MutableList<Todo>> =
        combine(mutableListOf(), todoListAll, state) { _, todos, state ->
            when (state) {
                TodoState.ACTIVE -> {
                    todos.filter { !it.isCompleted }.toMutableList()
                }
                TodoState.COMPLETE -> {
                    todos.filter { it.isCompleted }.toMutableList()
                }
                else -> todos
            }

        }

    fun getTodoList() {
        viewModelScope.launch {
            try {
                val todos = repository.getTodoList()
                todos.collect {
                    todoListAll.postValue(it)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun addTodo(title: String = "") {
        val lastId = todoListAll.value?.lastOrNull()?.id ?: 0
        val todo = Todo(lastId + 1, title, false)
        val list = todoListAll.value
        list?.add(todo)
        viewModelScope.launch {
            repository.addTodo(todo)
            todoListAll.postValue(list)
        }
    }

    fun deleteTodo(id: Int) {
        val list = todoListAll.value
        list?.removeAll { it.id == id }
        viewModelScope.launch {
            repository.deleteTodo(id)
            todoListAll.postValue(list)
        }
    }

    fun selectTab(position: Int) {
        state.postValue(TodoState.values()[position])
    }
}
