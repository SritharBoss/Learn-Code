package com.srithar.cscmoi.ui.main_screen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.srithar.cscmoi.domain.model.Customer
import com.srithar.cscmoi.domain.model.CustomerRequest
import com.srithar.cscmoi.ui.common.CommonViewModel
import my.nanihadesuka.compose.LazyColumnScrollbar
import my.nanihadesuka.compose.ScrollbarSettings

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PersonListScrollSection(dataList: List<Customer>, context: Context, listState: LazyListState) {

    var currentCustomer: CustomerRequest? by remember { mutableStateOf(null) }
    val haptic = LocalHapticFeedback.current
    val commonViewModel: CommonViewModel= hiltViewModel()
    val viewModel: MainViewModel= hiltViewModel()

    LazyColumnScrollbar(
        state = listState,
        settings = ScrollbarSettings.Default
    ){
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize().padding(8.dp)
        ) {
            items(dataList, key = { person -> person.id }) { item ->
                CustomerCardView(
                    person = item,
                    isTranslated = viewModel.isTranslated,
                    modifier = Modifier.animateItemPlacement().combinedClickable(
                        onLongClick = {
                            haptic.performHapticFeedback(HapticFeedbackType.LongPress)

                            if (commonViewModel.isServerReachable) {
                                currentCustomer = CustomerRequest(
                                    id = item.id,
                                    page_no = "${item.page_no}",
                                    first_name = item.first_name,
                                    tr_first_name = item.tr_first_name,
                                    village = item.village,
                                    tr_village = item.tr_village,
                                    last_name = item.last_name,
                                    note = item.note
                                )
                                viewModel.isAddCustomerDialogOpen = true
                            } else {
                                Toast.makeText(
                                    context,
                                    "Server is Offline. Cannot make Edit",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        onClick = {
                            viewModel.loadTransactions(commonViewModel,item.id.toString(), item.first_name+". "+item.last_name+" ==>  ₹ ${item.amount}", context)
                        }
                    ))
            }

        }

    }

    if (viewModel.isAddCustomerDialogOpen) {
        AddUserDialog(
            customer = currentCustomer,
            onDismiss = { viewModel.isAddCustomerDialogOpen = false; currentCustomer = null }
        )
    }
}