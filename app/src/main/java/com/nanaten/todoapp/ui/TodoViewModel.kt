package com.nanaten.todoapp.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nanaten.todoapp.db.Todo
import com.nanaten.todoapp.domain.TodoUseCase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import javax.inject.Inject

class TodoViewModel @Inject constructor(private val useCase: TodoUseCase) : ViewModel() {
    val todoList = MutableLiveData<MutableList<Todo>>(mutableListOf())
    private val disposables = CompositeDisposable()

    fun getTodoList() {
        useCase.getTodoList()
            .subscribe({
                todoList.postValue(it)
            }, {
                it.printStackTrace()
            })
            .addTo(disposables)
    }

    fun addTodo(title: String = "") {
        val lastId = todoList.value?.lastOrNull()?.id ?: 0
        val entity = Todo(lastId + 1, title, false)
        val todos = todoList.value
        todos?.add(entity)
        todoList.postValue(todos)
        useCase.addTodo(todoList.value?.toList() ?: emptyList())
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}