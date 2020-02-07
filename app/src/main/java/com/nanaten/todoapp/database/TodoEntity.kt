package com.nanaten.todoapp.database

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Data層で使用するObject
 * RealmObjectとしてデータを保持しておきたくないので、
 * ViewModelに転送する段階でTodoオブジェクトに変換する
 */
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