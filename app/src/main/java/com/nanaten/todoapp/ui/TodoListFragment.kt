package com.nanaten.todoapp.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.nanaten.todoapp.R
import com.nanaten.todoapp.adapter.TodoAdapter
import com.nanaten.todoapp.databinding.FragmentTodoListBinding
import com.nanaten.todoapp.di.viewmodel.ViewModelFactory
import com.nanaten.todoapp.util.autoCleared
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class TodoListFragment : DaggerFragment() {

    private var binding: FragmentTodoListBinding by autoCleared()
    private val mAdapter = TodoAdapter()
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: TodoViewModel by viewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_todo_list, container, false)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            todoModel = viewModel
            todoRv.layoutManager = LinearLayoutManager(context)
            todoRv.adapter = mAdapter
            val tabs = TodoTab.values().toList()
            tabs.forEach { tab ->
                todoTab.addTab(binding.todoTab.newTab().setText(tab.tabName))
            }
            todoFab.setOnClickListener {
                viewModel.addTodo("test")
            }
        }
        viewModel.todoList.observe(viewLifecycleOwner, Observer {
            mAdapter.setData(it)
        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getTodoList()
    }
}

enum class TodoTab(val value: Int, val tabName: String) {
    ACTIVE(0, "ACTIVE"),
    ALL(1, "ALL"),
    COMPLETE(2, "COMPLETED")
}