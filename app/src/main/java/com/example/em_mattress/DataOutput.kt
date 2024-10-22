package com.example.em_mattress

import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.em_mattress.ui.theme.Em_MattressTheme

@Composable
fun DataOutputScreen(navController: NavController, modifier: Modifier = Modifier) {
    var text by remember{ mutableStateOf("") }
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Enter Order Number",
                modifier = modifier,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
            OutlinedTextField(
                value = text,
                onValueChange = {text = it },
                label = { Text("X Digit Order Number") }
            )
            Text(
                text = "Displaying Order Number", //Add variable for order number $
                modifier = modifier,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
            Text(
                text = "Attributes", //Add attributes (perhaps in a table)
                modifier = modifier,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
            Text(
                text = "Link", //Add variable for attributes
                modifier = modifier,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            )
            Button(
                onClick = {navController.popBackStack()},
                content = { Text("Write to NFC", fontSize = MaterialTheme.typography.headlineLarge.fontSize) }, //change to show the CSV name
                modifier = Modifier.padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors()
            )
            //Image(painter = , contentDescription = )
            Text(
                text = "Address",
                modifier = modifier,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
            Button(
                onClick = {navController.popBackStack()},
                content = { Text("Go Back", fontSize = MaterialTheme.typography.headlineSmall.fontSize) }, //change to show the CSV name
                modifier = Modifier.padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors()
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DataOutputScreenPreview() {
    Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
        Em_MattressTheme{
            DataOutputScreen(navController = rememberNavController())
        }
    }
}