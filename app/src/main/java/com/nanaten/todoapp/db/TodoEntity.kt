package com.nanaten.todoapp.db

import io.realm.RealmObject


open class TodoEntity(
    var title: String = "",
    var isCompleted: Boolean = false
) : RealmObject()