package com.nanaten.todoapp.di

import com.nanaten.todoapp.domain.TodoRepository
import com.nanaten.todoapp.domain.TodoRepositoryImpl
import com.nanaten.todoapp.domain.TodoUseCase
import com.nanaten.todoapp.domain.TodoUseCaseImpl
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

    @Singleton
    @Provides
    @JvmStatic
    fun provideUseCase(repository: TodoRepository): TodoUseCase =
        TodoUseCaseImpl(repository)
}