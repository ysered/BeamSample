package com.ysered.beamsample.di

import com.ysered.beamsample.AddTaskDialogFragment
import com.ysered.beamsample.MainActivity
import com.ysered.beamsample.ReceiveActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class MainModule {
    @ContributesAndroidInjector
    abstract fun contributeToMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeToReceiveActivity(): ReceiveActivity

    @ContributesAndroidInjector
    abstract fun contributeToAddTaskDialog(): AddTaskDialogFragment
}
