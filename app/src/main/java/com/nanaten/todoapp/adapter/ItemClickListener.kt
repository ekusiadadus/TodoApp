package com.nanaten.todoapp.adapter

import android.view.View


interface ItemClickListener {
    fun onItemClick(index: Int, view: View)
}