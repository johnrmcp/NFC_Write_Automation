package com.example.em_mattress.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.em_mattress.NavAndViewModel.SharedViewModel
import com.example.em_mattress.ui.theme.Em_MattressTheme
import com.example.NFCitems.NFCUtil
import com.example.em_mattress.ui.theme.Purple40
import kotlinx.coroutines.delay


@Composable
fun DataOutputScreen(navController: NavController,
                     sharedViewModel: SharedViewModel,
                     modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf("") }
    //get orderArray from sharedViewModel
    val orderArray = sharedViewModel.orderArray
    //font size of important info
    val important = 20
    //a variable which holds the order numbers
    val ordernumbers: MutableList<String> by remember { mutableStateOf(mutableListOf<String>()) }
    //remember the state of the drop down menu
    val isDropDownExpanded = remember { mutableStateOf(false) }
    //remember the position of the item in the dropdown menu which is selected
    val itemPosition = remember { mutableStateOf(0) }
    //boolean to determine visibility of dialog
    var showDialog by remember { mutableStateOf(false) }
    //specifies the value in the dialog for quantity
    val showQuantityDialog = remember { mutableStateOf(false) }
    //defines the nfc payload (required because of disk set)
    var nfcpayload by remember { mutableStateOf<String?>(null) }
    //defines the image for the nfc payload
    var image by remember { mutableStateOf<String?>(null) }
    //create a list of just order numbers
    if (ordernumbers.size < orderArray.size - 1){
        for (i in 0..<orderArray.size) {
            Log.d("DataOutputScreen2", "${orderArray[i].ordernumber}")
            ordernumbers.add(orderArray[i].ordernumber!!)
        }
    }
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
            Row(horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(color = MaterialTheme.colorScheme.primary)
                        .clickable { isDropDownExpanded.value = true }
                        .padding(12.dp)
                        .clip(CircleShape)
                ) {
                    Text(
                        text = ordernumbers[itemPosition.value],
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize
                    )
                }
                Button(
                    onClick = {
                        if (itemPosition.value < ordernumbers.size - 1) {
                            itemPosition.value += 1
                        }
                    },
                    content = {
                        Text(
                            "Next Order",
                            fontSize = MaterialTheme.typography.headlineSmall.fontSize
                        )
                    }, //change to show the CSV name
                    modifier = Modifier.padding(start = 16.dp),
                    colors = ButtonDefaults.buttonColors(),
                )
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
                text = if (orderArray[itemPosition.value].producttype!!.contains("Keychain + Personalized")) {"Keychain + Disk"}
                else if (orderArray[itemPosition.value].producttype!!.contains("Fragrance")){"Fragrance Refill"}
                else if (orderArray[itemPosition.value].producttype!!.contains("Personalized Disks")){"Disk Set"}
                else if (orderArray[itemPosition.value].producttype!!.contains("Keychain")){"Empty Keychain"}
                else {"Vent Record Player"},
                modifier = Modifier.padding(bottom = 5.dp),
                fontSize = important.sp,
                color = Purple40
            )
            Text(
                text = "Variant:",
                modifier = Modifier.padding(0.dp),
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
            Text(
                text = "${orderArray[itemPosition.value].variant}",
                modifier = Modifier.padding(bottom = 5.dp),
                fontSize = important.sp,
                color = Purple40
            )
            Text(
                text = "Quantity:",
                modifier = Modifier.padding(0.dp),
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
            if (orderArray[itemPosition.value].quantity == "1"){
                Text(
                    text = "${orderArray[itemPosition.value].quantity}",
                    modifier = Modifier.padding(bottom = 5.dp),
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                )
            }else{
                Text(
                    text = "${orderArray[itemPosition.value].quantity}",
                    modifier = Modifier.padding(bottom = 5.dp),
                    fontSize = important.sp,
                    color = Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }
            //the following code is for an alert dialog that notifies the user that the quantity is over 1
            LaunchedEffect(orderArray[itemPosition.value].quantity) {
                if (orderArray[itemPosition.value].quantity?.toInt()!!>1) {
                    showQuantityDialog.value = true
                    delay(5000L)
                    showQuantityDialog.value = false
                }
            }
            if (showQuantityDialog.value) {
                QuantityDialog(onDismiss = { showQuantityDialog.value = false })
            }

            Text(
                text = "Address:",
                modifier = Modifier.padding(0.dp),
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
            Text(
                text = "${orderArray[itemPosition.value].name}" + "  " + "${orderArray[itemPosition.value].address}" + "  " + "${orderArray[itemPosition.value].phone}",
                modifier = Modifier.padding(bottom = 5.dp),
                fontSize = MaterialTheme.typography.bodySmall.fontSize
            )

            Text(
                text = "Image:",
                modifier = Modifier.padding(5.dp),
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
            if (orderArray[itemPosition.value].image == "" &&  orderArray[itemPosition.value].producttype!!.contains("Fragrance")) {
                Box(modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://cdn.shopify.com/s/files/1/0717/0437/9609/files/Fragrance_Alert.png?v=1731559154")
                            .crossfade(true)
                            .build(),
                        contentDescription = "Fragrance Image Down :(",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.clip(CircleShape),
                    )
                }
            }else if (orderArray[itemPosition.value].image == "" &&  orderArray[itemPosition.value].producttype!!.contains("Empty Disk-Case Keychain")){
                Box(modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://cdn.shopify.com/s/files/1/0717/0437/9609/files/Fragrance_Alert.png?v=1731559154")
                            .crossfade(true)
                            .build(),
                        contentDescription = "Empty Image Down :(",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.clip(CircleShape),
                    )
                }
            }else if (orderArray[itemPosition.value].image == ""){
                Box(modifier = Modifier
                    .height(100.dp)
                    .width(100.dp)) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data("https://cdn.shopify.com/s/files/1/0717/0437/9609/files/Blank.png?v=1731547382")
                            .crossfade(true)
                            .build(),
                        contentDescription = "Blank Image Down :(",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.clip(CircleShape),
                    )
                }
            } else if (orderArray[itemPosition.value].producttype!!.contains("Personalized Disks")){
                Row(modifier = Modifier
                    .height(75.dp)
                    .width(300.dp)) {
                    Box(modifier = Modifier
                        .height(75.dp)
                        .width(75.dp)) {
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
                    Box(modifier = Modifier
                        .height(75.dp)
                        .width(75.dp)) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("${orderArray[itemPosition.value].image2}")
                                .crossfade(true)
                                .build(),
                            contentDescription = "Order Image :)",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.clip(CircleShape),
                        )
                    }
                    Box(modifier = Modifier
                        .height(75.dp)
                        .width(75.dp)) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("${orderArray[itemPosition.value].image3}")
                                .crossfade(true)
                                .build(),
                            contentDescription = "Order Image :)",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.clip(CircleShape),
                        )
                    }
                    Box(modifier = Modifier
                        .height(75.dp)
                        .width(75.dp)) {
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data("${orderArray[itemPosition.value].image4}")
                                .crossfade(true)
                                .build(),
                            contentDescription = "Order Image :)",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.clip(CircleShape),
                        )
                    }
                }
            }else {
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

            if (orderArray[itemPosition.value].producttype!!.contains("Fragrance")){ //defines the case where there is a fragrance

            } else if (orderArray[itemPosition.value].producttype!!.contains("Personalized Disks")) { //defines the case where there is a disk set
                Row(modifier = Modifier
                    .height(75.dp)
                    .width(300.dp)) {
                    Box(modifier = Modifier
                        .height(75.dp)
                        .width(75.dp)) {
                        Button(
                            onClick = {showDialog = true
                                NFCUtil.boxon.value = true
                                nfcpayload = orderArray[itemPosition.value].music
                                image = orderArray[itemPosition.value].image},
                            content = { Text("W1", fontSize = important.sp) }, //change to show the CSV name
                            modifier = Modifier.padding(top = 16.dp),
                            colors = ButtonDefaults.buttonColors()
                        )
                    }
                    Box(modifier = Modifier
                        .height(75.dp)
                        .width(75.dp)) {
                        Button(
                            onClick = {showDialog = true
                                NFCUtil.boxon.value = true
                                nfcpayload = orderArray[itemPosition.value].music2
                                image = orderArray[itemPosition.value].image2},
                            content = { Text("W2", fontSize = important.sp) }, //change to show the CSV name
                            modifier = Modifier.padding(top = 16.dp),
                            colors = ButtonDefaults.buttonColors()
                        )
                    }
                    Box(modifier = Modifier
                        .height(75.dp)
                        .width(75.dp)) {
                        Button(
                            onClick = {showDialog = true
                                NFCUtil.boxon.value = true
                                nfcpayload = orderArray[itemPosition.value].music3
                                image = orderArray[itemPosition.value].image3},
                            content = { Text("W3", fontSize = important.sp) }, //change to show the CSV name
                            modifier = Modifier.padding(top = 16.dp),
                            colors = ButtonDefaults.buttonColors()
                        )
                    }
                    Box(modifier = Modifier
                        .height(75.dp)
                        .width(75.dp)) {
                        Button(
                            onClick = {showDialog = true
                                NFCUtil.boxon.value = true
                                nfcpayload = orderArray[itemPosition.value].music4
                                image = orderArray[itemPosition.value].image4},
                            content = { Text("W4", fontSize = important.sp) }, //change to show the CSV name
                            modifier = Modifier.padding(top = 16.dp),
                            colors = ButtonDefaults.buttonColors()
                        )
                    }
                }
            } else { //defines the case where there is a keychain or a spinner
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
                    onClick = {showDialog = true
                        NFCUtil.boxon.value = true
                              nfcpayload = orderArray[itemPosition.value].music
                              image = orderArray[itemPosition.value].image},
                    content = { Text("Write to NFC", fontSize = MaterialTheme.typography.headlineLarge.fontSize) }, //change to show the CSV name
                    modifier = Modifier.padding(top = 16.dp),
                    colors = ButtonDefaults.buttonColors()
                )
            }
            if (!NFCUtil.boxon.value){showDialog = false}

            if (showDialog) {
                val onDismiss = { showDialog = false }
                NFCUtil.changepayload(nfcpayload!!)
                NFCUtil.turnon()
                NFCDialog(onDismiss, image)
            } else {
                NFCUtil.turnoff()
            }


            Button(
                onClick = {navController.popBackStack()},
                content = { Text("Go Back", fontSize = MaterialTheme.typography.headlineSmall.fontSize) }, //change to show the CSV name
                modifier = Modifier.padding(top = 16.dp),
                colors = ButtonDefaults.buttonColors()
            )

        }
    }
}

@Composable
fun NFCDialog(
    onDismiss: () -> Unit,
    image: String?
) {
    Dialog(onDismissRequest = { onDismiss() }){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(NFCUtil.text.value,
                        modifier = Modifier.padding(all = 20.dp),
                    fontSize = MaterialTheme.typography.headlineSmall.fontSize)
                Box(
                    modifier = Modifier
                        .height(120.dp)
                        .width(120.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(image)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Order Image :)",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.clip(CircleShape),
                    )
                }
                Button(
                    onClick = { onDismiss() },
                    content = { Text("Cancel Write", fontSize = MaterialTheme.typography.headlineSmall.fontSize) }, //change to show the CSV name
                    modifier = Modifier.padding(top = 16.dp),
                    colors = ButtonDefaults.buttonColors()
                )
            }
        }
    }
//    AlertDialog(
//        title = {
//            Text(NFCUtil.text.value)
//        },
//        onDismissRequest = {
//            onDismiss()
//        },
//        confirmButton = {
//            TextButton(
//                onClick = {
//                    onDismiss()
//                }
//            ) {
//                Text("Cancel Write")
//            }
//        }
//    )
}

@Composable
fun QuantityDialog(
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Alert") },
        text = { Text("This Order Has A Quantity Over 1") },
        confirmButton = {
            Button(onClick = { onDismiss() }) {
                Text("OK")
            }
        }
    )
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