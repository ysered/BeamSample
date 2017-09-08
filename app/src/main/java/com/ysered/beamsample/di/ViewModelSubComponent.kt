package com.ysered.beamsample.di

import com.ysered.beamsample.TasksViewModel
import dagger.Subcomponent


@Subcomponent
interface ViewModelSubComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build(): ViewModelSubComponent
    }
    val tasksViewModel: TasksViewModel
}