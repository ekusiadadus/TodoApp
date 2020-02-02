package com.nanaten.todoapp.adapter

import androidx.recyclerview.widget.RecyclerView


abstract class BaseRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var itemClickListener: ItemClickListener

    fun setOnItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    fun getItemClickListener(): ItemClickListener {
        return itemClickListener
    }
}

