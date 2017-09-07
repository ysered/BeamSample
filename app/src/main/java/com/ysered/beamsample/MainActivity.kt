package com.ysered.beamsample

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import android.nfc.NfcEvent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.v7.app.AppCompatActivity
import com.ysered.beamsample.util.toast


class MainActivity : AppCompatActivity(), NfcAdapter.CreateNdefMessageCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (!nfcAdapter.isEnabled) {
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
