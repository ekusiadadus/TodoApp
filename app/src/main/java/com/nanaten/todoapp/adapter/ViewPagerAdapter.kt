package com.nanaten.todoapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nanaten.todoapp.R
import com.nanaten.todoapp.database.Todo
import com.nanaten.todoapp.databinding.ListItemViewPagerBinding
import com.nanaten.todoapp.ui.TodoState

class ViewPagerAdapter : BaseRecyclerViewAdapter() {
    private var list: List<Todo> = emptyList()

    override fun getItemCount(): Int {
        return TodoState.values().size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is PageViewHolder) {
            return
        }
        val todos = when (position) {
            TodoState.ACTIVE.value -> {
                this.list.filter { !it.isCompleted }
            }
            TodoState.COMPLETE.value -> {
                this.list.filter { it.isCompleted }
            }
            else -> {
                this.list
            }
        }
        holder.bind(todos, this.getItemClickListener())
    }

    fun update(list: List<Todo>) {
        this.list = list
        TodoState.values().forEach { notifyItemChanged(it.value) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PageViewHolder(parent)
    }

    class PageViewHolder(
        parent: ViewGroup,
        private val binding: ListItemViewPagerBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.list_item_view_pager, parent, false
        )
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(list: List<Todo>, listener: ItemClickListener) {
            val adapter = TodoAdapter()
            binding.todoRv.layoutManager = LinearLayoutManager(binding.root.context)
            binding.todoRv.adapter = adapter
            adapter.update(list)
            adapter.setOnItemClickListener(listener)
        }
    }
}