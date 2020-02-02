package com.nanaten.todoapp.di.ui

import com.nanaten.todoapp.di.ActivityScope
import com.nanaten.todoapp.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class MainActivityBuilder {
    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            MainFragmentModule::class
        ]
    )
    abstract fun bindMainActivity(): MainActivity
}