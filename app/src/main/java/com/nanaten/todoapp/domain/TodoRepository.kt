package com.nanaten.todoapp.domain

import com.nanaten.todoapp.database.Todo
import com.nanaten.todoapp.database.TodoEntity
import io.realm.Realm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface TodoRepository {
    suspend fun getTodoList(): Flow<MutableList<Todo>>
    fun addTodo(todo: Todo)
    fun deleteTodo(id: Int)
    fun clearCompleted()
    fun checkChanged(todo: Todo)
}

class TodoRepositoryImpl : TodoRepository {
    override suspend fun getTodoList(): Flow<MutableList<Todo>> {
        return flow {
            Realm.getDefaultInstance().use { realm ->
                val entity = realm.where(TodoEntity::class.java).findAll()
                val todos = entity?.map { Todo(it) }?.toMutableList()
                todos?.sortBy { it.id }
                emit(todos ?: mutableListOf())
            }
        }
    }

    override fun addTodo(todo: Todo) {
        val entity = TodoEntity(todo)
        Realm.getDefaultInstance().use {
            it.executeTransaction { realm ->
                realm.insertOrUpdate(entity)
            }
        }
    }

    override fun deleteTodo(id: Int) {
        Realm.getDefaultInstance().use {
            it.executeTransaction { realm ->
                val entity = realm.where(TodoEntity::class.java).equalTo("id", id).findFirst()
                entity?.deleteFromRealm()
            }
        }
    }

    override fun clearCompleted() {
        Realm.getDefaultInstance().use {
            it.executeTransaction { realm ->
                val entity =
                    realm.where(TodoEntity::class.java).equalTo("isCompleted", true).findAll()
                entity?.deleteAllFromRealm()
            }
        }
    }

    override fun checkChanged(todo: Todo) {
        Realm.getDefaultInstance().use {
            it.executeTransaction { realm ->
                val entity = realm.where(TodoEntity::class.java).equalTo("id", todo.id).findFirst()
                entity?.isCompleted = todo.isCompleted
            }
        }
    }
}