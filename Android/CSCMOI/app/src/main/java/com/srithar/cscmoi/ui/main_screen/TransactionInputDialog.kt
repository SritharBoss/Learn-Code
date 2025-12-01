package com.srithar.cscmoi.ui.main_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.srithar.cscmoi.data.network.model.TransactionRequest
import com.srithar.cscmoi.domain.model.TransactionData
import com.srithar.cscmoi.ui.common.CommonViewModel
import com.srithar.cscmoi.utils.Utils
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTransactionDialog(
    transaction: TransactionData?,
    onDismiss: () -> Unit
) {
    val isEdit:Boolean=(transaction!=null)

    val viewModel: MainViewModel= hiltViewModel()

    var type by remember { mutableStateOf(transaction?.type ?: viewModel.transactionTypes[0]) }
    var date by remember { mutableStateOf(transaction?.date ?: Utils.getTodayDateString()) }
    var amount by remember { mutableStateOf(if(transaction!=null) ""+transaction.amount else "") }
    var isCr by remember { mutableStateOf((transaction?.amount?:0) >= 0) }
    var notes by remember { mutableStateOf(transaction?.note ?: "") }
    var errorMsg by remember { mutableStateOf("") }

    if(errorMsg.isNotBlank()){
        LaunchedEffect(Unit) {
            delay(5000L)
            errorMsg=""
        }
    }

    val context = LocalContext.current
    val commonViewModel: CommonViewModel = hiltViewModel()

    AnimatedVisibility(
        visible = true,
    ) {
        AlertDialog(
            onDismissRequest = onDismiss,
            properties= DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false),
            confirmButton = {
                TextButton(onClick = {
                    // ✅ Validate date format yyyy-MM-dd
                    val regex = Regex("""\d{4}-\d{2}-\d{2}""")
                    if (!regex.matches(date)) {
                        errorMsg = "Invalid date format. $date"
                        return@TextButton
                    }

                    val amt = Utils.updateAmount(isCr,amount).toIntOrNull() ?: 0

                    if (amt==0) {
                        errorMsg= "Invalid Amount"
                        return@TextButton
                    }

                    if(viewModel.selectedID==0){
                        errorMsg= "Unknown Error. Error Code 333"
                    }

                    val request = TransactionRequest(
                        type = type,
                        date = date,
                        amount = "$amt",
                        note = notes,
                        customer_id = viewModel.selectedID,
                        id=(transaction?.id ?: "0")
                    )

                    if(isEdit){
                        viewModel.editTransaction(commonViewModel,
                            request,
                            onSuccess = { onDismiss(); viewModel.loadTransactions(commonViewModel,viewModel.selectedID.toString(), viewModel.selectedPersonName,context) },
                            onError = { commonViewModel.errorMessage = it },
                            context
                        )
                    }else{
                        viewModel.createTransaction(
                            commonViewModel,request,
                            onSuccess = { onDismiss(); viewModel.loadTransactions(commonViewModel,viewModel.selectedID.toString(), viewModel.selectedPersonName,context) },
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
            title = { Text("Add TXN - "+viewModel.selectedPersonName, style = MaterialTheme.typography.titleMedium) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

                    if (errorMsg.isNotBlank()) {
                        Text(
                            text = errorMsg,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    // Type dropdown with free text
                    var expanded by remember { mutableStateOf(false) }
                    ExposedDropdownMenuBox (
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded }
                    ) {
                        OutlinedTextField(
                            value = type,
                            onValueChange = { type = it },
                            label = { Text("Type") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier.fillMaxWidth().menuAnchor()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            viewModel.transactionTypes.forEach { option ->
                                DropdownMenuItem(
                                    text = { Text(option) },
                                    onClick = {
                                        type = option
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                    val datePickerState = rememberDatePickerState()
                    val showDatePicker = remember { mutableStateOf(false) }
                    val dateText = remember { mutableStateOf("") }

                    // Date picker button instead of text input
                    OutlinedTextField(
                        value = date,
                        onValueChange = { date = it },
                        label = { Text("Date (yyyy-MM-dd)") },
                        trailingIcon = {
                            IconButton(onClick = { showDatePicker.value = true }) {
                                Icon(Icons.Filled.DateRange, contentDescription = "Open date picker")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { showDatePicker.value = true }
                    )

                    if (showDatePicker.value) {
                        DatePickerDialog (
                            onDismissRequest = { showDatePicker.value = false },
                            confirmButton = {
                                TextButton(
                                    onClick = {
                                        showDatePicker.value = false
                                        val selectedMillis = datePickerState.selectedDateMillis
                                        if (selectedMillis != null) {
                                            dateText.value = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(selectedMillis))
                                            date=dateText.value
                                        }
                                    }
                                ) {
                                    Text("OK")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDatePicker.value = false }) {
                                    Text("Cancel")
                                }
                            },
                            properties = DialogProperties(usePlatformDefaultWidth = false)
                        ) {
                            DatePicker(state = datePickerState)
                        }
                    }

                    // Amount with Dr/Cr toggle
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        OutlinedTextField(
                            value = amount,
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            onValueChange = { amount = it; isCr = !amount.startsWith("-")},
                            label = { Text("Amount") },
                            modifier = Modifier.weight(1f).onFocusChanged{ focusState ->
                                amount=Utils.updateAmount(isCr,amount)
                            }
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(if (isCr) "Cr." else "Dr.")
                        Switch(checked = isCr, onCheckedChange = {
                            isCr = it
                            amount=Utils.updateAmount(isCr,amount)
                        })
                    }

                    // Notes
                    OutlinedTextField(
                        value = notes,
                        singleLine = false,
                        onValueChange = { notes = it },
                        label = { Text("Notes") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        )
    }
}