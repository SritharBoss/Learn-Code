package com.srithar.composeapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }
}

@Composable
fun Content(){
    MaterialTheme {
        val (openDialog, setOpenDialog) = remember { mutableStateOf(false) }
        Surface(modifier = Modifier.padding(12.dp)) {
            Button(onClick = { setOpenDialog(true) }) {
                Text("Show Dialog")
            }
            if (openDialog) { // Conditionally display the dialog
                MyAlertDialog(
                    showDialog = openDialog,
                    onDismissRequest = { setOpenDialog(false) },
                    onConfirmClick = {
                        // Your confirm action logic
                        Log.d("AlertDialog", "Confirmed!")
                        setOpenDialog(false) // Close the dialog
                    },
                    title = "Important Action",
                    text = "Are you sure you want to perform this action?"
                )
            }

            MyAlertDialog(openDialog,{setOpenDialog(false)},{setOpenDialog(false)},"Confirmation","Are you sure you want to delete this item?")
        }
    }
}
@Composable
fun Greeting(name:String, modifier: Modifier = Modifier){
    Text(text = "Hello $name!", modifier = modifier)
}

@Composable
fun MyAlertDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmClick: () -> Unit,
    title: String,
    text: String
) {
    if (showDialog) {
        AlertDialog(
            properties = DialogProperties(
                dismissOnClickOutside = false,
                dismissOnBackPress = false
            ),
            onDismissRequest = onDismissRequest,
            title = {
                Text(text = title)
            },
            text = {
                Text(text = text)
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirmClick()
                        onDismissRequest() // Dismiss dialog after confirm
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = onDismissRequest
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview(){
    Content()
}