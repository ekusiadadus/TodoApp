package com.nanaten.todoapp.database

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey


open class Todo(
    @PrimaryKey
    var id: Int = 1,
    var title: String = "",
    var isCompleted: Boolean = false
) : RealmObject()