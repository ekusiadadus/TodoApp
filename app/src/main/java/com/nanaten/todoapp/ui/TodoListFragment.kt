package com.nanaten.todoapp.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.android.material.tabs.TabLayout
import com.nanaten.todoapp.R
import com.nanaten.todoapp.adapter.ItemClickListener
import com.nanaten.todoapp.adapter.Operation
import com.nanaten.todoapp.adapter.TodoAdapter
import com.nanaten.todoapp.database.Todo
import com.nanaten.todoapp.databinding.FragmentTodoListBinding
import com.nanaten.todoapp.di.viewmodel.ViewModelFactory
import com.nanaten.todoapp.util.autoCleared
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class TodoListFragment : DaggerFragment(), ItemClickListener {

    private var binding: FragmentTodoListBinding by autoCleared()
    private val mAdapter = TodoAdapter()
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: TodoViewModel by activityViewModels { viewModelFactory }

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
            val tabs = TodoState.values().toList()
            tabs.forEach { tab ->
                todoTab.addTab(binding.todoTab.newTab().setText(tab.tabName))
            }
            todoTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    viewModel.selectTab(tab?.position ?: 0)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }
            })
            todoFab.setOnClickListener {
                addTodo()
            }
        }
        mAdapter.setOnItemClickListener(this)
        viewModel.todoList.observe(viewLifecycleOwner, Observer {
            mAdapter.update(it)
        })
        viewModel.animation.observe(viewLifecycleOwner, Observer {
            if (it) {
                binding.animationView.progress = 0F
                binding.animationView.playAnimation()
            }
        })
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getTodoList()
    }

    override fun onItemClick(operation: Operation, view: View) {
        when (operation) {
            Operation.DELETE -> {
                deleteTodo(view.tag as Int)
            }
            Operation.CHECK_CHANGED -> {
                val todo = view.tag as Todo
                viewModel.checkChanged(todo)
            }
            else -> return
        }
    }

    private fun deleteTodo(id: Int) {
        context?.let {
            val dialog = MaterialDialog(it)
            dialog.apply {
                title(R.string.delete)
                message(R.string.confirm_delete)
                positiveButton(R.string.yes) {
                    viewModel.deleteTodo(id)
                }
                negativeButton(R.string.no)
                cornerRadius(10.0F)
                show()
            }
        }
    }

    private fun addTodo() {
        context?.let {
            val dialog = MaterialDialog(it)
            dialog.apply {
                title(R.string.add)
                message(R.string.please_input_title)
                cornerRadius(10.0F)
                customView(R.layout.dialog_item_add_todo, scrollable = true)
                val title = getCustomView().findViewById<EditText>(R.id.todo_title)
                val addButton = getCustomView().findViewById<ImageView>(R.id.add_button)

                addButton.setOnClickListener {
                    if (title.text.toString().isBlank()) {
                        Toast.makeText(context, "タイトルを入力してください", Toast.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }
                    viewModel.addTodo(title.text.toString())
                    dismiss()
                }
                show()
            }
        }
    }
}

enum class TodoState(val value: Int, val tabName: String) {
    ACTIVE(0, "ACTIVE"),
    ALL(1, "ALL"),
    COMPLETE(2, "COMPLETED")
}