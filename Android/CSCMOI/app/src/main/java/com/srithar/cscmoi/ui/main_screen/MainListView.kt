package com.srithar.cscmoi.ui.main_screen

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.srithar.cscmoi.data.network.client.RetrofitClient
import com.srithar.cscmoi.data.pref.PreferenceManager
import com.srithar.cscmoi.domain.model.Customer
import com.srithar.cscmoi.domain.model.TransactionData
import com.srithar.cscmoi.ui.common.CommonViewModel
import com.srithar.cscmoi.ui.common.ConfirmationDialog
import com.srithar.cscmoi.ui.dashbord.DashboardScreen
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty1

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreenWithApi() {

    val viewModel: MainViewModel= hiltViewModel()
    val commonViewModel: CommonViewModel= hiltViewModel()

    val context = LocalContext.current

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope ()

    if(!viewModel.isMainScreenStarted){
        viewModel.fetchCustomers(commonViewModel,context)
        viewModel.loadVillages(commonViewModel)
    }

    viewModel.isMainScreenStarted = true

    viewModel.isTranslated=PreferenceManager.getIsTranslated(context)

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val haptic = LocalHapticFeedback.current

    val data = viewModel.filteredList
    val isLoading = viewModel.isLoading
    val isRefreshing = viewModel.isRefreshing

    var showMenu by remember { mutableStateOf(false) }

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    // Main layout
    Scaffold(
        topBar = {
            var isAddCustomerShown by remember { mutableStateOf(false) }
            TopAppBar(
                title = { Text("${if(commonViewModel.isServerReachable) "\uD83D\uDFE2" else "\uD83D\uDD34"} - ${if(commonViewModel.isDashBoard) "DashBoard" else "Relatives List"}") },
                scrollBehavior = scrollBehavior,
                actions = {
                    IconButton(onClick = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        showMenu = false
                        commonViewModel.isDashBoard=!commonViewModel.isDashBoard
                    }) {
                        if(commonViewModel.isDashBoard){
                            Icon(Icons.Default.List, contentDescription = "List View")
                        }else{
                            Icon(Icons.Default.Dashboard, contentDescription = "DashBoard")
                        }
                    }

                    // 3-dot icon
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                    }

                    var sortByExpanded by remember { mutableStateOf(false) }

                    // Dropdown menu
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {

                        if(!commonViewModel.isDashBoard){
                            DropdownMenuItem(
                                text = { Text("Refresh") },
                                onClick = {
                                    showMenu = false
                                    viewModel.refresh(commonViewModel,context)
                                    coroutineScope.launch { listState.animateScrollToItem(0) }
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                                }
                            )

                            DropdownMenuItem(
                                text = { Text(if(viewModel.isTranslated) "English" else "தமிழ்") },
                                onClick = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    viewModel.isTranslated = !viewModel.isTranslated
                                    PreferenceManager.setIsTranslated(context,viewModel.isTranslated)
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.Language, contentDescription = "Language")
                                }
                            )
                        }

                        DropdownMenuItem(
                            text = { Text(if(commonViewModel.isDarkTheme) "Light Theme" else "Dark Theme") },
                            onClick = {
                                showMenu = false
                                commonViewModel.isDarkTheme=!commonViewModel.isDarkTheme
                                Toast.makeText(context,"Theme Changed",Toast.LENGTH_SHORT).show()
                            },
                            leadingIcon = {
                                Icon(Icons.Default.DarkMode, contentDescription = "DarkMode")
                            }
                        )

                        if(!commonViewModel.isDashBoard){
                            DropdownMenuItem(
                                text = { Text("Add User") },
                                onClick = {
                                    showMenu = false
                                    if(commonViewModel.isServerReachable){
                                        isAddCustomerShown = true
                                    }else{
                                        Toast.makeText(context,"Server is Offline. Cannot Add User",Toast.LENGTH_LONG).show()
                                    }
                                    //Toast.makeText(context,"இனிமேதான் வரும்",Toast.LENGTH_SHORT).show()
                                },
                                leadingIcon = {
                                    Icon(Icons.Default.PersonAdd, contentDescription = "Add User")
                                }
                            )

                            DropdownMenuItem(
                                text = { Text("Sort By") },
                                onClick = {
                                    showMenu = false
                                    sortByExpanded = true
                                },
                                leadingIcon = {
                                    Icon(Icons.AutoMirrored.Filled.Sort, contentDescription = "Add User")
                                }
                            )
                        }

                        DropdownMenuItem(
                            text = { Text("Logout") },
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                showMenu = false
                                commonViewModel.isLoggedIn=false
                                RetrofitClient.setToken("")
                                viewModel.isMainScreenStarted=false
                                commonViewModel.isDashBoard=true
                            },
                            leadingIcon = {
                                Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = "Logout")
                            }
                        )
                    }

                    DropdownMenu(
                        expanded = sortByExpanded,
                        onDismissRequest = { sortByExpanded = false }
                    ) {
                        listOf<KProperty1<Customer, *>>(
                            Customer::id,
                            Customer::village,
                            Customer::first_name,
                            Customer::last_name,
                            Customer::note,
                            Customer::amount,
                            Customer::max
                        ).forEach { prop->
                            DropdownMenuItem(
                                text = { Text("Sort by ${if(prop.name == "max") "Created Date" else prop.name.replace('_',' ').toTitleCase()}") },
                                onClick = {
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    if(viewModel.sortBy.name.equals(prop.name)){
                                        viewModel.isDescending=!viewModel.isDescending
                                    }else{
                                        viewModel.sortBy= prop as KProperty1<Customer, *>
                                        viewModel.isDescending=false
                                    }
                                    viewModel.onSortBy()
                                    coroutineScope.launch{
                                        listState.animateScrollToItem(0)
                                    }
                                }, leadingIcon = {
                                    if(viewModel.sortBy.name.equals(prop.name)){
                                        if(!viewModel.isDescending){
                                            Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Descending")
                                        }else{
                                            Icon(Icons.Default.KeyboardArrowUp, contentDescription = "Ascending")
                                        }
                                    }
                                }
                            )
                        }

                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )

            if(isAddCustomerShown){
                AddUserDialog(
                    customer = null,
                    onDismiss = { isAddCustomerShown = false }
                )
            }
        }
    ) { padding ->
        Row(Modifier.fillMaxSize().padding(padding)) {

            Column(
                Modifier
                    .weight(1f)
                    .nestedScroll(scrollBehavior.nestedScrollConnection)
            ) {
                if(commonViewModel.isDashBoard){
                    DashboardScreen()
                }else{
                    SwipeRefresh(
                        state = rememberSwipeRefreshState(isRefreshing),
                        onRefresh = { viewModel.refresh(commonViewModel,context); coroutineScope.launch { listState.animateScrollToItem(0) } }
                    ) {
                        Column(Modifier.fillMaxSize()) {
                            FixedSearchSection(
                                onSearch = { name, otherType, otherQuery ->
                                    viewModel.applyFilters(
                                        name,
                                        otherType,
                                        otherQuery

                                    )
                                },
                                onScrollToTop = {
                                    coroutineScope.launch { listState.animateScrollToItem(0) }
                                }
                            )

                            if (isLoading ) {
                                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                    CircularProgressIndicator()
                                }
                            }else{
                                PersonListScrollSection(
                                    dataList = data,
                                    context,
                                    listState
                                )
                            }
                        }
                    }
                }
            }

            var currentTxn:TransactionData? by remember { mutableStateOf(null) }

            if (viewModel.isSheetOpen) {
                if(commonViewModel.isServerReachable){
                    if (viewModel.isAddTxnDialogOpen) {
                        AddTransactionDialog(
                            transaction = currentTxn,
                            onDismiss = { viewModel.isAddTxnDialogOpen = false; currentTxn = null }
                        )
                    }
                }

                ModalBottomSheet(
                    onDismissRequest = { viewModel.isSheetOpen = false },
                    sheetState = sheetState
                ) {
                    Text(
                        viewModel.selectedPersonName,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.titleMedium
                    )

                    if (commonViewModel.isServerReachable){
                        OutlinedButton (
                            onClick = {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                viewModel.loadTransactionTypes(commonViewModel)
                                viewModel.isAddTxnDialogOpen = true
                            },
                            modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp)
                        ) {
                            Text("Add New Transaction")
                        }
                    }

                    if(viewModel.selectedTransactions.isNotEmpty()){

                        val listState=rememberLazyListState()
                        var deleteConfirmation by remember { mutableStateOf(false) }

                        LazyColumn(state=listState,modifier = Modifier.fillMaxHeight(0.8f)) {
                            items(viewModel.selectedTransactions, key = { td -> td.id }) { txn ->
                                var showOptions by remember { mutableStateOf(false) }

                                Card(
                                    modifier = Modifier.fillMaxWidth().padding(8.dp).pointerInput(Unit) {
                                        detectTapGestures(
                                            onLongPress = { offset ->

                                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                                if(commonViewModel.isServerReachable){
                                                    showOptions = true
                                                }else{
                                                    Toast.makeText(context,"Server is Offline. Cannot make Edit or Delete",Toast.LENGTH_LONG).show()
                                                }
                                            }
                                        )
                                    },
                                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                                ){
                                    Column(modifier = Modifier.padding(16.dp)) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Text("Type      : ${txn.type}")
                                            Text(txn.date)
                                        }
                                        Text("Amount   : ₹ "+(txn.amount), fontWeight = FontWeight.Bold,color  = if (txn.amount >=0) MaterialTheme.colorScheme.primary else Color.Red)
                                        if(txn.note.isNotEmpty()){
                                            Text("Note      : ${txn.note.replace("\n","; ")}")
                                        }
                                        Text("Created  : ${txn.created_date}")
                                        Spacer(Modifier.height(8.dp))
//                                        Button(onClick = {
//
//                                        }) {
//                                            Text("Delete")
//                                        }
                                    }

                                    DropdownMenu(
                                        expanded = showOptions,
                                        onDismissRequest = { showOptions = false },
                                    ) {
                                        DropdownMenuItem(
                                            text = { Text("Edit") },
                                            onClick = {
                                                // Handle option 2 click
                                                showOptions = false
                                                currentTxn=txn
                                                viewModel.isAddTxnDialogOpen=true
                                            },
                                            leadingIcon = {
                                                Icon(Icons.Default.Edit, contentDescription = "Edit")
                                            }
                                        )

                                        DropdownMenuItem(
                                            text = { Text("Delete") },
                                            onClick = {
                                                // Handle option 1 click
                                                showOptions = false
                                                deleteConfirmation=true
                                            },
                                            leadingIcon = {
                                                Icon(Icons.Default.Delete, contentDescription = "Delete")
                                            }
                                        )
                                    }
                                    if(deleteConfirmation){
                                        ConfirmationDialog (message="Are you sure you want to Delete?",
                                            properties= DialogProperties(dismissOnClickOutside = false, dismissOnBackPress = false),
                                            onConfirm = {
                                                viewModel.deleteTransaction(commonViewModel,
                                                    txn.id,
                                                    onSuccess = { viewModel.loadTransactions(commonViewModel,viewModel.selectedID.toString(), viewModel.selectedPersonName,context) },
                                                    onError = { commonViewModel.errorMessage = it },
                                                    context
                                                )
                                                deleteConfirmation=false
                                            }, onDismiss = {deleteConfirmation=false})
                                    }
                                }
                            }
                        }
                    }else{
                        Box(
                            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.8f),
                            contentAlignment = Alignment.Center
                        ){
                            Text(text="\uD83D\uDE15 Empty Here \uD83D\uDCED")
                        }
                    }
                }
            }

        }
    }
}

fun String.toTitleCase(): String =
    this.lowercase()
        .split(" ")
        .joinToString(" ") { word ->
            word.replaceFirstChar { it.uppercase() }
        }