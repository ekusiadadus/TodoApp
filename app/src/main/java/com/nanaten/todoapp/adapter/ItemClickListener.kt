package com.nanaten.todoapp.adapter

import android.view.View


interface ItemClickListener {
    fun onItemClick(operation: Operation, view: View)
}