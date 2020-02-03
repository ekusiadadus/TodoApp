package com.nanaten.todoapp.domain

import com.nanaten.todoapp.database.Todo
import io.realm.Realm
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

interface TodoRepository {
    suspend fun getTodoList(): Flow<MutableList<Todo>>
    fun addTodo(list: List<Todo>)
}

class TodoRepositoryImpl : TodoRepository {
    override suspend fun getTodoList(): Flow<MutableList<Todo>> {
        return flow {
            val realm = Realm.getDefaultInstance()
            val entity = realm.where(Todo::class.java).findAll().toMutableList()
            emit(entity)
            realm.close()
        }
    }

    override fun addTodo(list: List<Todo>) {
        val realm = Realm.getDefaultInstance()
        realm.use {
            it.executeTransaction { realm ->
                realm.insertOrUpdate(list)
            }
        }
    }
}