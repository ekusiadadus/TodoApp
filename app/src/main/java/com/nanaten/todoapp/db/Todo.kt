package com.nanaten.todoapp.db

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Todo(
    val title: String,
    val isCompleted: Boolean
) : Parcelable