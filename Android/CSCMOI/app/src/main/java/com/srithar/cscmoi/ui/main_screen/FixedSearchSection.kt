package com.srithar.cscmoi.ui.main_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.srithar.cscmoi.domain.model.Customer
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty1

@Composable
fun FixedSearchSection(onSearch: (name: String, otherType: KProperty1<Customer, *>, otherQuery:String) -> Unit, onScrollToTop: () -> Unit) {
    var nameQuery by remember { mutableStateOf("") }
    var otherQuery by remember { mutableStateOf("") }
    var isExpanded by remember { mutableStateOf(false) }

    var otherType:KProperty1<Customer,*> by remember {mutableStateOf(Customer::village)}

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = nameQuery,
            onValueChange = {
                nameQuery = it
                onSearch(nameQuery,otherType,otherQuery)
                onScrollToTop()
            },
            label = { Text("Name") },
            singleLine = true,
            modifier = Modifier.weight(1f),
            trailingIcon = {
                IconButton(onClick = { nameQuery=""; onSearch(nameQuery,otherType,otherQuery); onScrollToTop()}) {
                    Icon(Icons.Filled.Clear, contentDescription = "Clear Name")

                }
            }
        )

        Box(modifier = Modifier.weight(1f)){
            OutlinedTextField(
                value = otherQuery,
                onValueChange = {
                    otherQuery = it
                    onSearch(nameQuery,otherType,otherQuery)
                    onScrollToTop()
                },
                label = { Text(getName(otherType)) },
                singleLine = true,
                trailingIcon = {
                    IconButton(onClick = { otherQuery=""; onSearch(nameQuery,otherType,otherQuery); isExpanded=!isExpanded }) {
                        Icon(Icons.Filled.ArrowDropDown, contentDescription = "Clear OtherType")
                    }
                }
            )

            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                listOf<KProperty1<Customer, *>>(
                    Customer::id,
                    Customer::village,
                    Customer::last_name,
                    Customer::note,
                    Customer::amount,
                    Customer::page_no
                ).forEach {
                    DropdownMenuItem(
                        text = { Text(getName(it)) },
                        onClick = {
                            isExpanded = false
                            otherType=it
                        }
                    )
                }
            }
        }

    }
}

fun getName(it:KProperty1<Customer, *>):String{
    return if(it.name == "max") "Created Date" else it.name.replace('_',' ').toTitleCase()
}