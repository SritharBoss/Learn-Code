package com.srithar.cscmoi.ui.main_screen

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srithar.cscmoi.data.network.client.RetrofitClient
import com.srithar.cscmoi.data.network.model.TransactionRequest
import com.srithar.cscmoi.domain.model.Customer
import com.srithar.cscmoi.domain.model.CustomerRequest
import com.srithar.cscmoi.domain.model.TransactionData
import com.srithar.cscmoi.room.AppDatabase
import com.srithar.cscmoi.ui.common.CommonViewModel
import com.srithar.cscmoi.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.reflect.KProperty1

@HiltViewModel
class MainViewModel @Inject constructor(): ViewModel() {

    //DATA
    var allData = listOf<Customer>()
    var filteredList by mutableStateOf<List<Customer>>(emptyList())
    var selectedTransactions by mutableStateOf<List<TransactionData>>(emptyList())
    var transactionTypes by mutableStateOf(listOf("EVENT","Marriage","Ear Boring","House Warming","MOI Feast"))
    var villages by mutableStateOf<List<String>>(listOf())


    //FLAGS - Transactions
    var isSheetOpen by mutableStateOf(false)
    var selectedPersonName by mutableStateOf("")
    var selectedID by mutableIntStateOf(0)
    var isAddTxnDialogOpen by mutableStateOf(false)

    //FLAGS - CustomerList
    var isAddCustomerDialogOpen by mutableStateOf(false)
    var isTranslated by mutableStateOf(false)
    var sortBy:KProperty1<Customer,*> by mutableStateOf(Customer::max)
    var isDescending by mutableStateOf(true)
    var isLoading by mutableStateOf(false)
    var isRefreshing by mutableStateOf(false)
    var isMainScreenStarted by mutableStateOf(false)

    fun loadTransactionTypes(commonViewModel: CommonViewModel) {
        viewModelScope.launch {
            transactionTypes = try {
                if(commonViewModel.isServerReachable){
                    RetrofitClient.api.getTransactionTypes()
                } else {
                    listOf("EVENT","Marriage","Ear Boring","House Warming","MOI Feast")
                }
            } catch (_: Exception) {
                listOf("EVENT","Marriage","Ear Boring","House Warming","MOI Feast")
            }
        }
    }

    fun loadTransactions(commonViewModel: CommonViewModel,id: String, name: String, context: Context) {
        viewModelScope.launch {
            try {
                selectedPersonName = name
                selectedID=id.toIntOrNull()?:0
                selectedTransactions = if(commonViewModel.isServerReachable){
                    RetrofitClient.api.getTransactions(id).sortedByDescending { it -> Utils.parseYYYYMMDDtoDate(it.created_date)}
                }else{
                    AppDatabase.getDatabase(context).transactionDataDao().getTransactionsForCustomer(id.toIntOrNull()?:0).sortedByDescending { it -> Utils.parseYYYYMMDDtoDate(it.created_date)}
                }
                isSheetOpen = true
            } catch (e: Exception) {
                Log.e("LoadTransactions", "Failed: ${e.message}")
                commonViewModel.errorMessage="Failed: ${e.message}"
                if(e.message?.contains("401") == true){
                    commonViewModel.isLoggedIn=false
                }
                e.message?.let {
                    if(it.contains("Failed to connect to", ignoreCase = true)){
                        commonViewModel.isServerReachable=false
                    }
                }
            }
        }
    }

    fun loadVillages(commonViewModel: CommonViewModel) {
        viewModelScope.launch {
            villages = try {
                if(commonViewModel.isServerReachable){
                    RetrofitClient.api.getVillages().villages
                } else {
                    listOf()
                }
            } catch (_: Exception) {
                listOf()
            }
        }
    }

    fun createTransaction(commonViewModel: CommonViewModel, request: TransactionRequest, onSuccess: () -> Unit, onError: (String) -> Unit, context: Context) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.createTransaction(request)
                Toast.makeText(context,
                    if (response.status.equals("Success",ignoreCase = true))  "Added Successfully" else "Failed - $response", Toast.LENGTH_SHORT).show()
                onSuccess()
                fetchCustomers(commonViewModel,context)
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
                if(e.message?.contains("401") == true){
                    commonViewModel.isLoggedIn=false
                }
                e.message?.let {
                    if(it.contains("Failed to connect to",ignoreCase = true)){
                        commonViewModel.isServerReachable=false
                    }
                }
            }
        }
    }

    fun createCustomer(commonViewModel: CommonViewModel,request: CustomerRequest, onSuccess: () -> Unit, onError: (String) -> Unit,context: Context) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.createCustomer(request)
                Toast.makeText(context,
                    if (response.status.equals("Success",ignoreCase = true))  "Added Successfully" else "Failed - $response", Toast.LENGTH_SHORT).show()
                onSuccess()
                fetchCustomers(commonViewModel,context)
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
                if(e.message?.contains("401") == true){
                    commonViewModel.isLoggedIn=false
                }
                e.message?.let {
                    if(it.contains("Failed to connect to",ignoreCase = true)){
                        commonViewModel.isServerReachable=false
                    }
                }
            }
        }
    }

    fun editTransaction(commonViewModel: CommonViewModel,request: TransactionRequest, onSuccess: () -> Unit, onError: (String) -> Unit,context: Context) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.editTransaction(request.id,request)
                Toast.makeText(context,if (response.status.equals("Success",ignoreCase = true))  "Edited Successfully" else "Failed - $response", Toast.LENGTH_SHORT).show()
                onSuccess()
                fetchCustomers(commonViewModel,context)
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
                if(e.message?.contains("401") == true){
                    commonViewModel.isLoggedIn=false
                }
                e.message?.let {
                    if(it.contains("Failed to connect to", ignoreCase = true)){
                        commonViewModel.isServerReachable=false
                    }
                }
            }
        }
    }

    fun editCustomer(commonViewModel: CommonViewModel,request: CustomerRequest, onSuccess: () -> Unit, onError: (String) -> Unit,context: Context) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.editCustomer("${request.id}",request)
                Toast.makeText(context,if (response.status.equals("Success",ignoreCase = true))  "Edited Successfully" else "Failed - $response", Toast.LENGTH_SHORT).show()
                onSuccess()
                fetchCustomers(commonViewModel,context)
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
                if(e.message?.contains("401") == true){
                    commonViewModel.isLoggedIn=false
                }
                e.message?.let {
                    if(it.contains("Failed to connect to", ignoreCase = true)){
                        commonViewModel.isServerReachable=false
                    }
                }
            }
        }
    }

    fun deleteTransaction(commonViewModel: CommonViewModel,id: String, onSuccess: () -> Unit, onError: (String) -> Unit,context: Context) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.api.deleteTransaction(id)
                Toast.makeText(context,if (response.status.equals("Success",ignoreCase = true))  "Deleted Successfully" else "Failed - $response", Toast.LENGTH_SHORT).show()
                onSuccess()
                fetchCustomers(commonViewModel,context)
            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
                if(e.message?.contains("401") == true){
                    commonViewModel.isLoggedIn=false
                }
                e.message?.let {
                    if(it.contains("Failed to connect to", ignoreCase = true)){
                        commonViewModel.isServerReachable=false
                    }
                }
            }
        }
    }

    fun downloadTransactionsToDB(commonViewModel: CommonViewModel,context:Context,onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val transactions = RetrofitClient.api.getTransactions()
                val db = AppDatabase.getDatabase(context)

                // Cache to DB
                db.transactionDataDao().clearAll()
                db.transactionDataDao().insertAll(
                    transactions.map {
                        TransactionData(
                            customer_id = it.customer_id,
                            type = it.type,
                            date = it.date.substring(0,10),
                            amount = it.amount,
                            note = it.note,
                            created_date = it.created_date.substring(0,10),
                            id = it.id
                        )
                    }
                )

            } catch (e: Exception) {
                onError(e.message ?: "Unknown error")
                e.message?.let {
                    if(it.contains("Failed to connect to",ignoreCase = true)){
                        commonViewModel.isServerReachable=false
                    }
                }
            }
        }
    }

    fun onSortBy(){
        allData=if(isDescending) {
            allData.sortedByDescending { it-> sortBy.get(it) as Comparable<Any> }
        }else{
            allData.sortedBy { it-> sortBy.get(it) as Comparable<Any> }
        }

        filteredList=if(isDescending) {
            filteredList.sortedByDescending { it-> sortBy.get(it) as Comparable<Any> }
        }else{
            filteredList.sortedBy { it-> sortBy.get(it) as Comparable<Any> }
        }

    }

    fun fetchCustomers(commonViewModel: CommonViewModel,context: Context) {
        viewModelScope.launch {
            isLoading = true
            commonViewModel.errorMessage = ""

            val db=AppDatabase.getDatabase(context)

            if(commonViewModel.isServerReachable){
                try {
                    val customers = if(isDescending) {
                        RetrofitClient.api.getCustomers().sortedByDescending { it-> sortBy.get(it) as Comparable<Any> }
                    }else{
                        RetrofitClient.api.getCustomers().sortedBy { it-> sortBy.get(it) as Comparable<Any> }
                    }
                    allData = customers
                    filteredList = customers

                    // Cache to DB
                    db.customerDao().clearAll()
                    db.customerDao().insertAll(
                        customers.map {
                            Customer(
                                id = it.id,
                                page_no = it.page_no,
                                village = it.village,
                                tr_village= it.tr_village ?: it.village,
                                first_name = it.first_name,
                                tr_first_name= it.tr_first_name ?: it.first_name,
                                last_name = it.last_name,
                                note = it.note,
                                amount = it.amount,
                                max=it.max
                            )
                        }
                    )

                    downloadTransactionsToDB(commonViewModel,context, onError = { it->
                        Toast.makeText(context.applicationContext, it, Toast.LENGTH_SHORT).show()
                    } )
                    Toast.makeText(context,"Online data cached into Local DB", Toast.LENGTH_SHORT).show()

                } catch (e: Exception) {
                    Log.e("Offline", "Fetch failed: ${e.message}")
                    // Load from cache
                    allData = db.customerDao().getAll().map {
                        Customer(
                            id = it.id,
                            page_no = it.page_no,
                            village=it.village,
                            tr_village= it.tr_village ?: it.village,
                            first_name = it.first_name,
                            tr_first_name= it.tr_first_name ?: it.first_name,
                            last_name = it.last_name,
                            note = it.note,
                            amount = it.amount,
                            max=it.max
                        )
                    }
                    filteredList = allData
                    commonViewModel.errorMessage = "Showing offline data"
                } finally {
                    isLoading = false
                }

            }else{
                allData = db.customerDao().getAll().map {
                    Customer(
                        id = it.id,
                        page_no = it.page_no,
                        village = it.village,
                        tr_village= it.tr_village ?: it.village,
                        first_name = it.first_name,
                        tr_first_name= it.tr_first_name ?: it.first_name,
                        last_name = it.last_name,
                        note = it.note,
                        amount = it.amount,
                        max=it.max
                    )
                }
                filteredList = allData
                commonViewModel.errorMessage = "Showing offline data"
                isLoading = false
            }

        }
    }


    fun refresh(commonViewModel: CommonViewModel,context: Context) {
        val db= AppDatabase.getDatabase(context)
        viewModelScope.launch {
            isRefreshing = true
            try {
                allData = if(isDescending) {
                    RetrofitClient.api.getCustomers().sortedByDescending { it-> sortBy.get(it) as Comparable<Any> }
                }else{
                    RetrofitClient.api.getCustomers().sortedBy { it-> sortBy.get(it) as Comparable<Any> }
                }
                filteredList = allData
                commonViewModel.errorMessage = ""
                commonViewModel.isServerReachable=true

                db.customerDao().clearAll()
                db.customerDao().insertAll(
                    allData.map {
                        Customer(
                            id = it.id,
                            page_no = it.page_no,
                            village = it.village,
                            tr_village= it.tr_village ?: it.village,
                            first_name = it.first_name,
                            tr_first_name= it.tr_first_name ?: it.first_name,
                            last_name = it.last_name,
                            note = it.note,
                            amount = it.amount,
                            max=it.max
                        )
                    }
                )

                downloadTransactionsToDB(commonViewModel,context, onError = { it->
                    Toast.makeText(context.applicationContext, it, Toast.LENGTH_SHORT).show()
                } )

                Toast.makeText(context.applicationContext,"Refreshed Successfully", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                commonViewModel.errorMessage = "Refresh failed: ${e.message}"
                if(e.message?.contains("401") == true){
                    commonViewModel.isLoggedIn=false
                }
                e.message?.let {
                    if(it.contains("Failed to connect to", ignoreCase = true)){
                        commonViewModel.isServerReachable=false
                        fetchCustomers(commonViewModel,context)
                    }
                }
            } finally {
                isRefreshing = false
            }
        }
    }

    fun applyFilters(nameQuery: String, byOther:KProperty1<Customer,*>,otherQuery: String) {

        if(byOther.name == "page_no" || byOther.name == "id"){
            filteredList = allData.filter {
                (if(isTranslated) it.tr_first_name?:it.first_name else it.first_name).contains(nameQuery, ignoreCase = true) &&
                        (byOther.get(it)).toString().equals(otherQuery, ignoreCase = true)
            }
        }else if(byOther.returnType.classifier == Int::class && (otherQuery.startsWith(">") || otherQuery.startsWith("<")) &&
            otherQuery.substring(1).toIntOrNull()!=null){

            filteredList =if(otherQuery.startsWith(">")){
                allData.filter {
                    (if(isTranslated) it.tr_first_name?:it.first_name else it.first_name).contains(nameQuery, ignoreCase = true) &&
                            (byOther.get(it) as Int) >= otherQuery.substring(1).toInt()
                }
            }else{
                allData.filter {
                    (if(isTranslated) it.tr_first_name?:it.first_name else it.first_name).contains(nameQuery, ignoreCase = true) &&
                            (byOther.get(it) as Int) <= ((otherQuery.substring(1).toInt())*-1)
                }
            }

        }else{
            filteredList = allData.filter {
                    (if(isTranslated) it.tr_first_name?:it.first_name else it.first_name).contains(nameQuery, ignoreCase = true) &&
                            (byOther.get(it)).toString().contains(otherQuery, ignoreCase = true)
            }
        }

    }

}