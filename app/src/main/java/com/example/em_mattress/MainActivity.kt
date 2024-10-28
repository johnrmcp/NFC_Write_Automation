package com.example.em_mattress

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.em_mattress.NavAndViewModel.SetupNavGraph
import com.example.em_mattress.ui.theme.Em_MattressTheme

class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Em_MattressTheme {
                navController = rememberNavController()
                SetupNavGraph(navController = navController)
            }
        }
    }
}