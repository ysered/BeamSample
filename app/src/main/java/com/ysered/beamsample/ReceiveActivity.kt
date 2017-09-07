package com.ysered.beamsample

import android.nfc.NdefMessage
import android.nfc.NfcAdapter
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView


class ReceiveActivity : AppCompatActivity() {

    private val messageText by lazy { findViewById<TextView>(R.id.messageText) }

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
        message.records.firstOrNull()?.let {
            messageText.text = String(it.payload)
        }
    }
}