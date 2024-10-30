 package com.example.em_mattress.composables

 import android.net.Uri
 import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
 import androidx.compose.material3.Button
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
 import androidx.compose.ui.platform.LocalContext
 import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.em_mattress.ui.theme.Em_MattressTheme
import androidx.navigation.compose.rememberNavController
 import com.example.em_mattress.CSVfunctions.Order
 import com.example.em_mattress.NavAndViewModel.Screen
 import com.example.em_mattress.NavAndViewModel.SharedViewModel
 import com.example.em_mattress.CSVfunctions.inputStreamToFileURI
 import com.example.em_mattress.CSVfunctions.parseCSV
 import java.io.File
 import java.io.InputStream

 @Composable
fun CSVReadScreen(navController: NavController,
                  sharedViewModel: SharedViewModel,
                  modifier: Modifier = Modifier) {

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
                text = "Enter CSV",
                modifier = modifier,
                fontSize = MaterialTheme.typography.headlineLarge.fontSize
            )

            val (isCalled, orderArray) = filePicker()

            if (isCalled) {
                Text(text = "File Acquired", fontSize = MaterialTheme.typography.bodyLarge.fontSize)
            } else {
                Text(text = "No File Selected", fontSize = MaterialTheme.typography.bodyLarge.fontSize)
            }

            Button(
                onClick = {
                    if (isCalled) {
                        navController.navigate(route = Screen.DataOutput.route)
                        sharedViewModel.updateOrderArray(neworderArray = orderArray)
                    } else {}
                },
                content = { Text("Proceed", fontSize = MaterialTheme.typography.headlineSmall.fontSize) },
                modifier = Modifier.padding(top = 16.dp),
            )
        }
    }
}


 @Composable
 fun filePicker(): Pair<Boolean, List<Order>> {
     var inputstream: InputStream? by remember { mutableStateOf(null) }
     val context = LocalContext.current
     var file: File by remember { mutableStateOf(File("")) }
     var orderArray: List<Order> by remember { mutableStateOf(mutableListOf<Order>()) }
     var isCalled: Boolean by remember { mutableStateOf(false) }

     val launcher = rememberLauncherForActivityResult(
         contract =  ActivityResultContracts.GetContent()
     ){ uri: Uri? ->
         if (uri != null) {
             //convert content Uri to InputStream
             inputstream = context.contentResolver.openInputStream(uri)
             Log.d("FilePicker", inputstream.toString())
             //receives inputstream and saves it as a temporary file in the context of this application
             file = inputStreamToFileURI(context, inputstream!!)
             Log.d("FilePicker", file.path.toString())
             //call parseCSV function to parse the file and save it as an object of class Order
             orderArray = parseCSV(file)
             isCalled = true
         } else {
             Log.d("FilePicker", "Null uri")
         }
     }

     Button(onClick = {
         launcher.launch("text/csv")
     }) {
         Text(text = "Select CSV File",
         fontSize = MaterialTheme.typography.headlineSmall.fontSize)
     }

     return Pair(isCalled, orderArray)
 }


@Preview(showBackground = true)
@Composable
fun CSVReadScreenPreview() {
    Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
        Em_MattressTheme{
            CSVReadScreen(navController = rememberNavController(), sharedViewModel = SharedViewModel())
        }
    }
}