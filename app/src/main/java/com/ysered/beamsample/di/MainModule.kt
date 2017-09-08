package com.ysered.beamsample.di

import com.ysered.beamsample.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class MainModule {
    @ContributesAndroidInjector
    abstract fun contributeToMainActivity(): MainActivity
}
