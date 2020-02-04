package com.nanaten.todoapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nanaten.todoapp.database.Todo
import com.nanaten.todoapp.domain.TodoRepository
import com.nanaten.todoapp.util.combine
import kotlinx.coroutines.delay
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
    val animation = MutableLiveData<Boolean>(false)

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

    fun clearCompleted() {
        val list = todoListAll.value
        list?.removeAll { it.isCompleted }
        viewModelScope.launch {
            repository.clearCompleted()
            todoListAll.postValue(list)
        }
    }

    fun checkChanged(todo: Todo) {
        val list = todoListAll.value
        list?.first { it.id == todo.id }?.isCompleted = todo.isCompleted
        viewModelScope.launch {
            repository.checkChanged(todo)

            if (todo.isCompleted) {
                setAnimation()
            }
            delay(300)
            todoListAll.postValue(list)
        }
    }

    fun selectTab(position: Int) {
        state.postValue(TodoState.values()[position])
    }

    private suspend fun setAnimation() {
        viewModelScope.launch {
            animation.postValue(true)
            delay(1000)
            animation.postValue(false)
        }
    }
}
