package com.ysered.beamsample.di

import android.app.Application
import com.ysered.beamsample.database.Repository
import com.ysered.beamsample.database.TasksDao
import com.ysered.beamsample.database.TasksDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(subcomponents = arrayOf(ViewModelSubComponent::class))
class AppModule {
    @Singleton
    @Provides
    fun providesViewModelFactory(builder: ViewModelSubComponent.Builder): ViewModelFactory =
            ViewModelFactory(builder.build())

    @Singleton
    @Provides
    fun providesDatabase(application: Application): TasksDatabase
            = TasksDatabase.create(application.applicationContext)

    @Singleton
    @Provides
    fun providesTasksDao(database: TasksDatabase): TasksDao
            = database.taskDao

    @Singleton
    @Provides
    fun providesRepository(tasksDao: TasksDao): Repository
            = Repository(tasksDao)
}
