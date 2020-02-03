package com.nanaten.todoapp.database

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class TodoEntity(
    @PrimaryKey
    var id: Int = 1,
    var title: String = "",
    var isCompleted: Boolean = false
) : RealmObject() {
    constructor(todo: Todo) : this() {
        this.id = todo.id
        this.title = todo.title
        this.isCompleted = todo.isCompleted
    }
}