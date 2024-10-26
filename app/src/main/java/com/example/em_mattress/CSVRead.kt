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
    //var text by remember{mutableStateOf("")}    //delete if replaced with file input field

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Log.d("FilePicker", "Arrived at home screen")
            Text(
                text = "Enter CSV",
                modifier = modifier,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
            /*
            OutlinedTextField( //see var declaration
                    value = text,
                    onValueChange = {text = it },
                    label = { Text("Label") }
            )
            */

            val butthole = FilePicker()

            Text(text = "Selected File: $butthole")
            
            Button(
                onClick = {
                    navController.navigate(route = Screen.DataOutput.route) },
                content = { Text("Proceed", fontSize = MaterialTheme.typography.headlineSmall.fontSize) },
                modifier = Modifier.padding(top = 16.dp),
            )
        }
    }
}


 @Composable
 fun FilePicker():String {
     var inputstream: InputStream? by remember { mutableStateOf(null) }
     val context = LocalContext.current
     var file: File by remember { mutableStateOf(File("")) }
     //val lineList = mutableListOf<String>()

     val launcher = rememberLauncherForActivityResult(
         contract =  ActivityResultContracts.GetContent()
     ){ uri: Uri? ->
         if (uri != null) {
             //convert content Uri to InputStream
             inputstream = context.contentResolver.openInputStream(uri)
             Log.d("FilePicker", inputstream.toString())
             //convert InputStream to File
             file = inputStreamToFileURI(context, inputstream!!)
             Log.d("FilePicker", file.path.toString())
             //call parseCSV function to parse the file and save it as an object of class Order
             parseCSV(file)

         //butthole?.bufferedReader()?.forEachLine { lineList.add(it) }
            //Log.d("FilePicker", "Final file: $lineList")
         } else {
             Log.d("FilePicker", "Null uri")
         }
     }

     Button(onClick = {
         launcher.launch("text/csv") // Specify the MIME type for CSV files
     }) {
         Text(text = "Select CSV File")
     }

     return file.toString()
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