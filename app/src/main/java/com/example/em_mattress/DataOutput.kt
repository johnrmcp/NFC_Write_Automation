package com.example.em_mattress

import android.util.Log
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
import androidx.compose.runtime.LaunchedEffect
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
fun DataOutputScreen(navController: NavController,
                     sharedViewModel: SharedViewModel,
                     modifier: Modifier = Modifier) {
    var text by remember{ mutableStateOf("") }
    //get orderArray from sharedViewModel
    val orderArray = sharedViewModel.orderArray
    //print first three lines of the CSV file when the order array is changed
    LaunchedEffect(key1 = orderArray) {
        Log.d("DataOutputScreen", orderArray[0].orderdate + orderArray[0].ordernumber + orderArray[0].producttype + orderArray[0].variant + orderArray[0].quantity + orderArray[0].name + orderArray[0].address + orderArray[0].phone + orderArray[0].image + orderArray[0].music + "/n" + orderArray[1].orderdate + orderArray[1].ordernumber + orderArray[1].producttype + orderArray[1].variant + orderArray[1].quantity + orderArray[1].name + orderArray[1].address + orderArray[1].phone + orderArray[1].image + orderArray[1].music + "/n" + orderArray[2].orderdate + orderArray[2].ordernumber + orderArray[2].producttype + orderArray[2].variant + orderArray[2].quantity + orderArray[2].name + orderArray[2].address + orderArray[2].phone + orderArray[2].image + orderArray[2].music + "/n" + orderArray[3].orderdate + orderArray[3].ordernumber + orderArray[3].producttype + orderArray[3].variant + orderArray[3].quantity + orderArray[3].name + orderArray[3].address + orderArray[3].phone + orderArray[3].image + orderArray[3].music)
    }

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
            DataOutputScreen(navController = rememberNavController(), sharedViewModel = SharedViewModel())
        }
    }
}