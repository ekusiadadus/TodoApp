package com.nanaten.todoapp.domain

import com.nanaten.todoapp.db.TodoEntity
import io.reactivex.Single
import io.realm.Realm

interface TodoRepository {
    fun getTodoList(): Single<List<TodoEntity>>
    fun addTodo(list: List<TodoEntity>)
}

class TodoRepositoryImpl : TodoRepository {
    override fun getTodoList(): Single<List<TodoEntity>> {
        return Single.create { emitter ->
            val realm = Realm.getDefaultInstance()
            val entity = realm.where(TodoEntity::class.java).findAll().toList()
            emitter.onSuccess(entity)
            realm.close()
        }
    }

    override fun addTodo(list: List<TodoEntity>) {
        val realm = Realm.getDefaultInstance()
        realm.use {
            it.executeTransaction { realm ->
                realm.insertOrUpdate(list)
            }
        }
    }
}