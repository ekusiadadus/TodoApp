package com.nanaten.todoapp.di

import com.nanaten.todoapp.repository.TodoRepository
import com.nanaten.todoapp.repository.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
internal object TodoModule {
    @Singleton
    @Provides
    @JvmStatic
    fun provideRepository(): TodoRepository =
        TodoRepositoryImpl()
}