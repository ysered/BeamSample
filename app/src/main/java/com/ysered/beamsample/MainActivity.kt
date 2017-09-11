package com.ysered.beamsample

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Observer
import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.NfcEvent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import com.ysered.beamsample.di.ViewModelFactory
import com.ysered.beamsample.util.debug
import com.ysered.beamsample.util.toast
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class MainActivity : AppCompatActivity(), LifecycleOwner, HasActivityInjector, NfcAdapter.CreateNdefMessageCallback {

    private val lifecycle = LifecycleRegistry(this)

    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject lateinit var viewModelFactory: ViewModelFactory
    private lateinit var tasksViewModel: TasksViewModel

    private lateinit var addFab: FloatingActionButton

    override fun getLifecycle(): Lifecycle = lifecycle

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addFab = findViewById(R.id.fab)
        addFab.setOnClickListener {
            AddTaskDialogFragment().show(supportFragmentManager, null)
        }

        tasksViewModel = viewModelFactory.create(TasksViewModel::class.java)

        tasksViewModel.tasks.observe(this, Observer { tasks ->
            debug("Observed tasks: $tasks")
        })

        val nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            toast(R.string.nfc_not_available)
        } else if (!nfcAdapter.isEnabled) {
            toast(R.string.enable_nfc)
            startActivity(Intent(Settings.ACTION_NFC_SETTINGS))
        } else if (!nfcAdapter.isNdefPushEnabled) {
            toast(R.string.enable_beam)
            startActivity(Intent(Settings.ACTION_NFCSHARING_SETTINGS))
        } else {
            nfcAdapter.setNdefPushMessageCallback(this, this)
        }
    }

    override fun createNdefMessage(nfcEvent: NfcEvent?): NdefMessage {
        val plainMessage = "Hello from ${Build.MANUFACTURER.toUpperCase()} ${Build.MODEL}!"
        val record = NdefRecord.createMime("text/plain", plainMessage.toByteArray())
        return NdefMessage(record)
    }
}
