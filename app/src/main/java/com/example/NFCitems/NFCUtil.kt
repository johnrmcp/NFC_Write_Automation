package com.example.NFCitems

import android.app.Activity
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NdefRecord.TNF_ABSOLUTE_URI
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import androidx.compose.runtime.mutableStateOf
import java.io.IOException
import java.nio.charset.Charset

object NFCUtil {

    var text = mutableStateOf("Bring NFC Tag to Phone")
    var ison = mutableStateOf(false)
    var boxon = mutableStateOf(false)
    var payload = mutableStateOf("")

    fun turnon(){
        ison.value = true
    }
    fun turnoff(){
        ison.value = false
    }
    fun changepayload(load: String){
        payload.value = load
    }

    fun createNFCMessage(payload: String, intent: Intent?){

//        val appRecord = NdefRecord.createApplicationRecord("com.spotify.music")

        val uriRecord = ByteArray(0).let { emptyByteArray ->
            NdefRecord(
                TNF_ABSOLUTE_URI,
                payload.toByteArray(Charset.forName("US-ASCII")),
                emptyByteArray,
                emptyByteArray
            )
        }

        val nfcMessage = NdefMessage(arrayOf(uriRecord))

        intent?.let {
            val tag = it.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
            val messageWrittenSuccessfully = writeMessageToTag(nfcMessage, tag)
            if (messageWrittenSuccessfully) {
                text.value = "Bring NFC Tag to Phone"
                boxon.value = false
            }else {
                text.value = "Write Failed, Try Again"
            }
        }
    }


    private fun writeMessageToTag(nfcMessage: NdefMessage, tag: Tag?): Boolean {

        try {
            val nDefTag = Ndef.get(tag)

            nDefTag?.let {
                it.connect()
                if (it.maxSize < nfcMessage.toByteArray().size) {
                    //Message to large to write to NFC tag
                    return false
                }
                if (it.isWritable) {
                    it.writeNdefMessage(nfcMessage)
                    it.close()
                    //Message is written to tag
                    return true
                } else {
                    //NFC tag is read-only
                    return false
                }
            }

            val nDefFormatableTag = NdefFormatable.get(tag)

            nDefFormatableTag?.let {
                try {
                    it.connect()
                    it.format(nfcMessage)
                    it.close()
                    //The data is written to the tag
                    return true
                } catch (e: IOException) {
                    //Failed to format tag
                    return false
                }
            }
            //NDEF is not supported
            return false

        } catch (e: Exception) {
            //Write operation has failed
        }
        return false
    }


    fun <T>enableNFCInForeground(nfcAdapter: NfcAdapter, activity: Activity, classType: Class<T>) {
        val pendingIntent = PendingIntent.getActivity(activity, 0,
            Intent(activity,classType).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0)
        //FLAG_ACTIVITY_SINGLE_TOP
        val nfcIntentFilter = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        val filters = arrayOf(nfcIntentFilter)

        val TechLists = arrayOf(arrayOf(Ndef::class.java.name), arrayOf(NdefFormatable::class.java.name))

        nfcAdapter.enableForegroundDispatch(activity, pendingIntent, filters, TechLists)
    }


    fun disableNFCInForeground(nfcAdapter: NfcAdapter,activity: Activity) {
        nfcAdapter.disableForegroundDispatch(activity)
    }
}