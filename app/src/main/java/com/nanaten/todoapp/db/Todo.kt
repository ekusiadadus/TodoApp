package com.nanaten.todoapp.db

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Todo(
    val id: Int,
    val title: String,
    var isCompleted: Boolean
) : Parcelable {
    constructor(entity: TodoEntity) : this(
        entity.id,
        entity.title,
        entity.isCompleted
    )
}