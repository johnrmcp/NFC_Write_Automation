 package com.example.em_mattress

import android.net.Uri
import android.widget.EditText
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import com.example.em_mattress.ui.theme.Em_MattressTheme
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import java.io.InputStream

@Composable
fun CSVReadScreen(navController: NavController, modifier: Modifier = Modifier) {
    //var text by remember{mutableStateOf("")}    //delete if replaced with file input field
    var selectedFilePath by remember { mutableStateOf("") }

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
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
            /*
            OutlinedTextField( //see var declaration
                    value = text,
                    onValueChange = {text = it },
                    label = { Text("Label") }
            )
            */
            FilePicker(onFileSelected = { uri ->
                    selectedFilePath = uri.path
            })

            Text(text = "Selected File: $selectedFilePath")
            /*
            Button(
                onClick = { navController.navigate(route = Screen.DataOutput.route) },
                content = { Text("Read CSV", fontSize = MaterialTheme.typography.headlineSmall.fontSize) },
                modifier = Modifier.padding(top = 16.dp),
            )
            */

            Text(
                text = "CSV Read",
                modifier = modifier,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
        }
    }
}

 //composable function which defines the file picker component
 @Composable
 fun FilePicker(onFileSelected: (Uri) -> Unit) {
     val launcher = rememberLauncherForActivityResult(
         ActivityResultContracts.GetContent()
     ) { uri ->
         if (uri != null) {
             onFileSelected(uri)
         } else {
             println("No file selected")
         }
     }

     Button(onClick = {
         launcher.launch("text/csv") // Specify the MIME type for CSV files
     }) {
         Text(text = "Select CSV File")
     }
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