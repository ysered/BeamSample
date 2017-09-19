package com.ysered.beamsample

import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ysered.beamsample.database.TaskEntity
import com.ysered.beamsample.util.ParcelableUtils
import com.ysered.beamsample.util.debug


class ReceiveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive)
    }

    override fun onResume() {
        super.onResume()
        intent?.let {
            if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
                val rawMessages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
                if (rawMessages.isNotEmpty()) {
                    showMessage(rawMessages.first() as NdefMessage)
                }
            }
        }
    }

    private fun showMessage(message: NdefMessage) {
        if (message.records.isNotEmpty()) {
            message.records.forEach {
                val entity = ParcelableUtils.parcelableFromBytes(it.payload, TaskEntity.CREATOR)
                debug("Received entity: $entity")
            }
        }
    }
}