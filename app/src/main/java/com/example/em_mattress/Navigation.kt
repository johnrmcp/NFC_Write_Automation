package com.example.em_mattress

import androidx.compose.runtime.Composable
import androidx.compose.runtime.internal.composableLambda
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun SetupNavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController = navController, startDestination = Screen.CSVRead.route) {
        composable(route = Screen.CSVRead.route) {
            val bob = CSVReadScreen(navController = navController)
            println(bob)
        }
        composable(route = Screen.DataOutput.route) {
            DataOutputScreen(navController = navController)
        }
    }
}
