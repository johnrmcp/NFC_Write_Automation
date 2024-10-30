package com.example.em_mattress

import android.content.Intent
import android.nfc.NfcAdapter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.em_mattress.NavAndViewModel.SetupNavGraph
import com.example.em_mattress.ui.theme.Em_MattressTheme
import com.example.NFCitems.NFCUtil

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    private var mNfcAdapter: NfcAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this)
        setContent {
            Em_MattressTheme {
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mNfcAdapter?.let {
            NFCUtil.enableNFCInForeground(it, this,  javaClass)
        }
    }

    override fun onPause() {
        super.onPause()
        mNfcAdapter?.let {
            NFCUtil.disableNFCInForeground(it,this)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        if (NFCUtil.ison.value) {
            NFCUtil.createNFCMessage(
                NFCUtil.payload.value,
                intent
            )
            NFCUtil.turnoff()
        }
    }
}