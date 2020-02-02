package com.nanaten.todoapp.db

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class TodoEntity(
    @PrimaryKey
    var id: Int = 1,
    var title: String = "",
    var isCompleted: Boolean = false
) : RealmObject() {
    constructor(model: Todo) : this() {
        this.id = model.id
        this.title = model.title
        this.isCompleted = model.isCompleted
    }
}