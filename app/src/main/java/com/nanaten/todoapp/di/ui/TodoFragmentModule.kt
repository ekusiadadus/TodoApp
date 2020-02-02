package com.nanaten.todoapp.di.ui

import androidx.lifecycle.ViewModel
import com.nanaten.todoapp.di.viewmodel.ViewModelKey
import com.nanaten.todoapp.ui.TodoListFragment
import com.nanaten.todoapp.ui.TodoViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
@Suppress("UNUSED")
internal abstract class MainFragmentModule {

    @Binds
    @IntoMap
    @ViewModelKey(TodoViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: TodoViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun provideMainFragment(): TodoListFragment

}