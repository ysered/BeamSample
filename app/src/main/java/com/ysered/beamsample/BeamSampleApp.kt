package com.ysered.beamsample

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import com.ysered.beamsample.di.DaggerAppComponent
import com.ysered.beamsample.di.InjectableFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class BeamSampleApp : Application(), HasActivityInjector, HasSupportFragmentInjector {

    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

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
                if (activity is FragmentActivity) {
                    activity.supportFragmentManager.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
                        override fun onFragmentCreated(manager: FragmentManager?, fragment: Fragment?, savedInstanceState: Bundle?) {
                            if (fragment is InjectableFragment) {
                                AndroidSupportInjection.inject(fragment)
                            }
                        }
                    }, true)
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

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentInjector
}
