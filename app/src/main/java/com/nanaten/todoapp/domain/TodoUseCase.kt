package com.nanaten.todoapp.domain

import com.nanaten.todoapp.db.Todo
import com.nanaten.todoapp.db.TodoEntity
import io.reactivex.Single
import javax.inject.Inject

interface TodoUseCase {
    fun getTodoList(): Single<MutableList<Todo>>
    fun addTodo(list: List<Todo>)
}

class TodoUseCaseImpl @Inject constructor(private val repository: TodoRepository) : TodoUseCase {
    override fun getTodoList(): Single<MutableList<Todo>> {
        return repository.getTodoList()
            .map {
                it.map { item ->
                    Todo(item)
                }.toMutableList()
            }

    }

    override fun addTodo(list: List<Todo>) {
        val todos = list.map { TodoEntity(it) }
        repository.addTodo(todos)
    }
}