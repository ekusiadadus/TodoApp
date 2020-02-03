package com.nanaten.todoapp.database


data class Todo(
    val id: Int,
    val title: String,
    var isCompleted: Boolean
) {
    constructor(entity: TodoEntity) : this(
        entity.id,
        entity.title,
        entity.isCompleted
    )
}