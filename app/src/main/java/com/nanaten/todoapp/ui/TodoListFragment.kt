package com.nanaten.todoapp.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.nanaten.todoapp.R
import com.nanaten.todoapp.adapter.TodoAdapter
import com.nanaten.todoapp.databinding.FragmentTodoListBinding
import com.nanaten.todoapp.util.autoCleared
import dagger.android.support.DaggerFragment


class TodoListFragment : DaggerFragment() {

    private var binding: FragmentTodoListBinding by autoCleared()
    private val mAdapter = TodoAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_todo_list, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.todoRv.layoutManager = LinearLayoutManager(context)
        binding.todoRv.adapter = mAdapter
        val tabs = TodoTab.values().toList()
        tabs.forEach { tab ->
            binding.todoTab.addTab(binding.todoTab.newTab().setText(tab.tabName))
        }
        return binding.root
    }


}

enum class TodoTab(val value: Int, val tabName: String) {
    ACTIVE(0, "ACTIVE"),
    ALL(1, "ALL"),
    COMPLETE(2, "COMPLETED")
}