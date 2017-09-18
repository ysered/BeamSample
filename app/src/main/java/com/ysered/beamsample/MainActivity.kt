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
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.ysered.beamsample.database.TaskEntity
import com.ysered.beamsample.di.ViewModelFactory
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

    private lateinit var tasksRv: RecyclerView
    private lateinit var tasksAdapter: TasksAdapter
    private lateinit var addFab: FloatingActionButton

    override fun getLifecycle(): Lifecycle = lifecycle

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tasksRv = findViewById(R.id.tasksRv)
        tasksRv.layoutManager = LinearLayoutManager(this)
        tasksRv.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        tasksAdapter = TasksAdapter(onTaskClickListener = object : TasksAdapter.OnTaskClickListener {
            override fun onClicked(task: TaskEntity) {
                tasksViewModel.updateTask(task)
            }
        })
        tasksRv.adapter = tasksAdapter

        addFab = findViewById(R.id.fab)
        addFab.setOnClickListener {
            AddTaskDialogFragment().show(supportFragmentManager, null)
        }

        tasksViewModel = viewModelFactory.create(TasksViewModel::class.java)

        tasksViewModel.tasks.observe(this, Observer { tasks ->
            tasks?.let {
                tasksAdapter.updateAll(it)
            }
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
