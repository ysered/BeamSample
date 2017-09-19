package com.ysered.beamsample

import android.app.Activity
import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.app.AppCompatActivity
import com.ysered.beamsample.database.TaskEntity
import com.ysered.beamsample.database.TasksDao
import com.ysered.beamsample.util.ParcelableUtils
import com.ysered.beamsample.util.debug
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import kotlinx.coroutines.experimental.run
import javax.inject.Inject


class ReceiveActivity : AppCompatActivity(), HasActivityInjector {

    @Inject lateinit var activityInjector: DispatchingAndroidInjector<Activity>
    @Inject lateinit var tasksDao: TasksDao

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive)
    }

    override fun onResume() {
        super.onResume()
        intent?.let {
            if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
                val rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
                extractAndSaveEntities(rawMessages)

                finish()
            }
        }
    }

    private fun extractAndSaveEntities(parcelables: Array<Parcelable>) {
        if (parcelables.isNotEmpty()) {
            launch(UI) {
                val message = parcelables.first() as NdefMessage
                if (message.records.isNotEmpty()) {
                    run(CommonPool) {
                        val receivedEntities = message.records
                                .map { ParcelableUtils.parcelableFromBytes(it.payload, TaskEntity.CREATOR) }
                                .toTypedArray()
                        debug("############# Received entities ###############")
                        receivedEntities.forEach { debug(it.toString()) }
                        tasksDao.save(*receivedEntities)
                    }
                }
            }
        }
    }
}