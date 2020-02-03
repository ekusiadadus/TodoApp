package com.nanaten.todoapp.di

import com.nanaten.todoapp.domain.TodoRepository
import com.nanaten.todoapp.domain.TodoRepositoryImpl
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