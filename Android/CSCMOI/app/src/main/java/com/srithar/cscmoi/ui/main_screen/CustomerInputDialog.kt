package com.srithar.cscmoi.ui.main_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.srithar.cscmoi.domain.model.CustomerRequest
import com.srithar.cscmoi.ui.common.CommonViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddUserDialog(
    customer: CustomerRequest?,
    onDismiss: () -> Unit
) {
    val isEdit:Boolean=(customer!=null)
    val viewModel: MainViewModel= hiltViewModel()
    val commonViewModel: CommonViewModel= hiltViewModel()
    viewModel.loadVillages(commonViewModel)

    var pageNo: String by remember { mutableStateOf(customer?.page_no ?: "0") }
    var firstName by remember { mutableStateOf(customer?.first_name ?: "") }
    var trFirstName by remember { mutableStateOf(customer?.tr_first_name ?: "") }
    var lastName by remember { mutableStateOf(customer?.last_name ?: "") }
    var village by remember { mutableStateOf(customer?.village ?: "") }
    var note by remember { mutableStateOf(customer?.note ?: "") }

    var errorMsg by remember { mutableStateOf("") }

    if(errorMsg.isNotBlank()){
        LaunchedEffect(Unit) {
            delay(5000L)
            errorMsg=""
        }
    }

    val context = LocalContext.current

    AnimatedVisibility(
        visible = true,
    ) {
        AlertDialog(
            onDismissRequest = onDismiss,
            properties= DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false),
            confirmButton = {
                TextButton(onClick = {

                    val request = CustomerRequest(
                        id = customer?.id ?: 0,
                        page_no = pageNo,
                        first_name = firstName,
                        tr_first_name = trFirstName,
                        last_name = lastName,
                        village = village,
                        note = note,
                        tr_village = ""
                    )

                    if(isEdit){
                        viewModel.editCustomer(commonViewModel,
                            request,
                            onSuccess = { onDismiss() },
                            onError = { commonViewModel.errorMessage = it },
                            context
                        )
                    }else{
                        viewModel.createCustomer(commonViewModel,
                            request,
                            onSuccess = { onDismiss(); },
                            onError = { commonViewModel.errorMessage = it },
                            context
                        )
                    }
                }) {
                    Text("Submit")
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
            },
            title = { Text("${if(customer!=null) "Edit " else "Add "} Customer", style = MaterialTheme.typography.titleMedium) },
            text = {
                Column(Modifier
                    .fillMaxWidth()
                    .heightIn(max = 400.dp) // prevent dialog from growing too tall
                    .verticalScroll(rememberScrollState()),verticalArrangement = Arrangement.spacedBy(8.dp)) {


                    if (errorMsg.isNotBlank()) {
                        Text(
                            text = errorMsg,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    OutlinedTextField(
                        value = pageNo,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = { pageNo = it.toIntOrNull()?.toString() ?: ""},
                        label = { Text("PageNo") },
                    )

                    // Type dropdown with free text
                    var expanded by remember { mutableStateOf(false) }
                    var tempVillages by remember { mutableStateOf(viewModel.villages) }
                    ExposedDropdownMenuBox (
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = village,
                            onValueChange = { village = it; expanded=true; tempVillages = viewModel.villages.filter { v -> v.contains(it, ignoreCase = true) }.take(3)},
                            label = { Text("Village") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.fillMaxWidth().menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            tempVillages.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        village = option
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = firstName,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = { firstName = it},
                        label = { Text("First Name") },
                    )

                    OutlinedTextField(
                        value = trFirstName,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = { trFirstName = it},
                        label = { Text("Translated First Name") },
                    )

                    OutlinedTextField(
                        value = lastName,
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        onValueChange = { lastName = it },
                        label = { Text("Last Name") },
                    )

                    // Notes
                    OutlinedTextField(
                        value = note,
                        singleLine = false,
                        onValueChange = { note = it },
                        label = { Text("Notes") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        )
    }
}