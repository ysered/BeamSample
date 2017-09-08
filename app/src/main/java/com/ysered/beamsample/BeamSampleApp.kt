package com.ysered.beamsample

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.ysered.beamsample.di.DaggerAppComponent
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class BeamSampleApp : Application(), HasActivityInjector {

    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
                .setApplication(this)
                .build()
                .inject(this)

        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity?, bundle: Bundle?) {
                if (activity is HasActivityInjector) {
                    AndroidInjection.inject(activity)
                }
            }

            // region empty members
            override fun onActivityPaused(p0: Activity?) {

            }

            override fun onActivityResumed(p0: Activity?) {

            }

            override fun onActivityStarted(p0: Activity?) {

            }

            override fun onActivityDestroyed(p0: Activity?) {

            }

            override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {

            }

            override fun onActivityStopped(p0: Activity?) {

            }
            // endregion
        })
    }

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector
}