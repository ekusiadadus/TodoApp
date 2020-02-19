package com.nanaten.todoapp.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.nanaten.todoapp.R
import com.nanaten.todoapp.databinding.FragmentTodoDetailBinding
import com.nanaten.todoapp.di.viewmodel.ViewModelFactory
import com.nanaten.todoapp.util.autoCleared
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class TodoDetailFragment : DaggerFragment() {

    private var binding: FragmentTodoDetailBinding by autoCleared()
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: TodoViewModel by activityViewModels { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_todo_detail, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.saveButton.setOnClickListener {
            viewModel.saveTodo()
            findNavController().popBackStack()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

}
