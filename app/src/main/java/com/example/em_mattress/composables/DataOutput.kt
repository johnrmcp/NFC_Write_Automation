package com.example.em_mattress.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.em_mattress.NavAndViewModel.SharedViewModel
import com.example.em_mattress.ui.theme.Em_MattressTheme


@Composable
fun DataOutputScreen(navController: NavController,
                     sharedViewModel: SharedViewModel,
                     modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }
    //get orderArray from sharedViewModel
    val orderArray = sharedViewModel.orderArray
    //a variable which holds the order numbers
    val ordernumbers: MutableList<String> by remember { mutableStateOf(mutableListOf<String>()) }
    //remember the state of the drop down menu
    val isDropDownExpanded = remember { mutableStateOf(false) }
    //remember the position of the item in the dropdown menu which is selected
    val itemPosition = remember { mutableStateOf(0) }
    //onLaunch {
    //display first three lines of CSV
    Log.d(
        "DataOutputScreen",
        orderArray[0].orderdate + orderArray[0].ordernumber + orderArray[0].producttype + orderArray[0].variant + orderArray[0].quantity + orderArray[0].name + orderArray[0].address + orderArray[0].phone + orderArray[0].image + orderArray[0].music + "/n" + orderArray[1].orderdate + orderArray[1].ordernumber + orderArray[1].producttype + orderArray[1].variant + orderArray[1].quantity + orderArray[1].name + orderArray[1].address + orderArray[1].phone + orderArray[1].image + orderArray[1].music + "/n" + orderArray[2].orderdate + orderArray[2].ordernumber + orderArray[2].producttype + orderArray[2].variant + orderArray[2].quantity + orderArray[2].name + orderArray[2].address + orderArray[2].phone + orderArray[2].image + orderArray[2].music + "/n" + orderArray[3].orderdate + orderArray[3].ordernumber + orderArray[3].producttype + orderArray[3].variant + orderArray[3].quantity + orderArray[3].name + orderArray[3].address + orderArray[3].phone + orderArray[3].image + orderArray[3].music
    )
    //create a list of just order numbers
    if (ordernumbers.size < orderArray.size - 1){
        for (i in 0..<orderArray.size) {
            Log.d("DataOutputScreen2", "${orderArray[i].ordernumber}")
            ordernumbers.add(orderArray[i].ordernumber!!)
        }
    }
    //}
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Enter Order Number:",
                modifier = Modifier.padding(20.dp),
                fontSize = MaterialTheme.typography.headlineLarge.fontSize
            )

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(color = MaterialTheme.colorScheme.primary)
                    .clickable { isDropDownExpanded.value = true }
                    .padding(12.dp)
                    .clip(CircleShape)
            ) {
                Text(text = ordernumbers[itemPosition.value],
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize)
            }
            DropdownMenu(
                expanded = isDropDownExpanded.value,
                onDismissRequest = {
                    isDropDownExpanded.value = false
                }) {
                ordernumbers.forEachIndexed { index, ordernumber ->
                    DropdownMenuItem(text = {
                        Text(text = ordernumber,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize)
                    },
                        onClick = {
                            Log.d("check", "$ordernumbers")
                            isDropDownExpanded.value = false
                            itemPosition.value = index
                        })
                }
            }
//            to display order number:
//            Text(
//                text = "Displaying Order: ${orderArray[itemPosition.value].ordernumber}",
//                modifier = Modifier.padding(20.dp),
//                fontSize = MaterialTheme.typography.headlineSmall.fontSize
//            )
            Text(
                text = "Order Date:",
                modifier = Modifier.padding(top = 20.dp),
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
            Text(
                text = "${orderArray[itemPosition.value].orderdate}",
                modifier = Modifier.padding(bottom = 5.dp),
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )
            Text(
                text = "Product:",
                modifier = Modifier.padding(0.dp),
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
            Text(
                text = "${orderArray[itemPosition.value].producttype}",
                modifier = Modifier.padding(bottom = 5.dp),
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )
            Text(
                text = "Variant:",
                modifier = Modifier.padding(0.dp),
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
            Text(
                text = "${orderArray[itemPosition.value].variant}",
                modifier = Modifier.padding(bottom = 5.dp),
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )
            Text(
                text = "Quantity:",
                modifier = Modifier.padding(0.dp),
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
            Text(
                text = "${orderArray[itemPosition.value].quantity}",
                modifier = Modifier.padding(bottom = 5.dp),
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )
            Text(
                text = "Address:",
                modifier = Modifier.padding(0.dp),
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
            Text(
                text = "${orderArray[itemPosition.value].name}" + "  " + "${orderArray[itemPosition.value].address}" + "  " + "${orderArray[itemPosition.value].phone}",
                modifier = Modifier.padding(bottom = 5.dp),
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )

            Text(
                text = "Image:",
                modifier = Modifier.padding(5.dp),
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
            if (orderArray[itemPosition.value].image == "") {
                Box(modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://cdn.shopify.com/s/files/1/0717/0437/9609/files/Disk_Type5.png?v=1729892968")
                            .crossfade(true)
                            .build(),
                        contentDescription = "Order Image :)",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.clip(CircleShape),
                    )
                }
            }else{
                Box(modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("${orderArray[itemPosition.value].image}")
                            .crossfade(true)
                            .build(),
                        contentDescription = "Order Image :)",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.clip(CircleShape),
                    )
                }
            }
            Text(
                text = "Music Link:",
                modifier = Modifier.padding(top=5.dp),
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
            Text(
                text = "${orderArray[itemPosition.value].music}",
                modifier = Modifier.padding(bottom = 5.dp),
                fontSize = MaterialTheme.typography.bodySmall.fontSize
            )

            Button(
                onClick = {},
                content = { Text("Write to NFC", fontSize = MaterialTheme.typography.headlineLarge.fontSize) }, //change to show the CSV name
                modifier = Modifier.padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors()
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