package com.nanaten.todoapp.database

/**
 * ViewModelで使用するObject
 */
data class Todo(
    val id: Int,
    var title: String,
    var content: String,
    var isCompleted: Boolean
) {
    constructor(entity: TodoEntity) : this(
        entity.id,
        entity.title,
        entity.content,
        entity.isCompleted
    )
}