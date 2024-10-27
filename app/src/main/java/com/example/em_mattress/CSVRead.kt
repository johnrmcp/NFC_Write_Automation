 package com.example.em_mattress

 import android.content.Context
 import android.net.Uri
import android.provider.DocumentsContract
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
 import androidx.compose.runtime.mutableIntStateOf
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
 import java.io.File
 import java.io.InputStream

 @Composable
fun CSVReadScreen(navController: NavController, modifier: Modifier = Modifier) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Log.d("Home", "Arrived at home screen1")
            Text(
                text = "Enter CSV",
                modifier = modifier,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )

            val (isCalled, orderArray) = filePicker()

            if (isCalled) {
                Text(text = "File Acquired")
            } else {
                Text(text = "No File Selected")
            }

            Button(
                onClick = {
                    navController.navigate(route = Screen.DataOutput.route)
                    Log.d("parseCSV", orderArray[0].orderdate + orderArray[0].ordernumber + orderArray[0].producttype + orderArray[0].variant + orderArray[0].quantity + orderArray[0].name + orderArray[0].address + orderArray[0].phone + orderArray[0].image + orderArray[0].music + "/n" + orderArray[1].orderdate + orderArray[1].ordernumber + orderArray[1].producttype + orderArray[1].variant + orderArray[1].quantity + orderArray[1].name + orderArray[1].address + orderArray[1].phone + orderArray[1].image + orderArray[1].music + "/n" + orderArray[2].orderdate + orderArray[2].ordernumber + orderArray[2].producttype + orderArray[2].variant + orderArray[2].quantity + orderArray[2].name + orderArray[2].address + orderArray[2].phone + orderArray[2].image + orderArray[2].music + "/n" + orderArray[3].orderdate + orderArray[3].ordernumber + orderArray[3].producttype + orderArray[3].variant + orderArray[3].quantity + orderArray[3].name + orderArray[3].address + orderArray[3].phone + orderArray[3].image + orderArray[3].music)
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
         launcher.launch("text/csv") // Specify the MIME type for CSV files
     }) {
         Text(text = "Select CSV File")
     }

     return Pair(isCalled, orderArray)
 }


@Preview(showBackground = true)
@Composable
fun CSVReadScreenPreview() {
    Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
        Em_MattressTheme{
            CSVReadScreen(navController = rememberNavController())
        }
    }
}