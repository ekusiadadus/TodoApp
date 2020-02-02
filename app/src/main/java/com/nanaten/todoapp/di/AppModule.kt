package com.nanaten.todoapp.di

import android.content.Context
import com.nanaten.todoapp.App
import dagger.Binds
import dagger.Module

@Module
abstract class AppModule {

    @Binds
    abstract fun provideContext(application: App): Context

}