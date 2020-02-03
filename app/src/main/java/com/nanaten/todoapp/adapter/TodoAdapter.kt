package com.nanaten.todoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.nanaten.todoapp.R
import com.nanaten.todoapp.database.Todo
import com.nanaten.todoapp.databinding.ListItemEmptyBinding
import com.nanaten.todoapp.databinding.ListItemTodoBinding

class TodoAdapter : BaseRecyclerViewAdapter() {
    private var list: List<Todo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.list_item_empty -> EmptyItemViewHolder(parent)
            else -> TodoItemViewHolder(parent)
        }
    }

    override fun getItemCount(): Int {
        return if (list.isEmpty()) 1 else list.size
    }

    fun update(list: List<Todo>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TodoItemViewHolder -> {
                holder.bind(list[position])
                holder.binding.listDelete.setOnClickListener {
                    it.tag = list[position].id
                    getItemClickListener().onItemClick(position, it)
                }
            }
            else -> return
        }
    }

    class TodoItemViewHolder(
        parent: ViewGroup,
        val binding: ListItemTodoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_todo,
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Todo) {
            binding.todo = item
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list.isEmpty()) {
            R.layout.list_item_empty
        } else {
            R.layout.list_item_todo
        }
    }

    class EmptyItemViewHolder(
        parent: ViewGroup,
        private val binding: ListItemEmptyBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.list_item_empty,
            parent,
            false
        )
    ) : RecyclerView.ViewHolder(binding.root)
}