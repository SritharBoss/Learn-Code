package com.srithar.cscmoi.ui.dashbord

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srithar.cscmoi.data.network.client.RetrofitClient
import com.srithar.cscmoi.domain.model.DashBoardData
import com.srithar.cscmoi.ui.common.CommonViewModel
import com.srithar.cscmoi.ui.main_screen.MainViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashBoardViewModel @Inject constructor() : ViewModel() {

    var dashBoardData by mutableStateOf(DashBoardData())

    fun getDashBoardData(commonViewModel: CommonViewModel,mainViewModel: MainViewModel) {
        viewModelScope.launch {
            try {
                if(commonViewModel.isServerReachable){
                    val data=RetrofitClient.api.getDashBoardData()
                    dashBoardData=if(data.isNotEmpty()) data[0] else DashBoardData()
                    dashBoardData.active_customers="${mainViewModel.allData.count { a -> a.amount >= 50 || a.amount <= -50 }}"
                }else{
                    dashBoardData=DashBoardData()
                    dashBoardData.customer_count="${mainViewModel.allData.count()}"
                    dashBoardData.active_customers="${mainViewModel.allData.count { a -> a.amount >= 50 || a.amount <= -50 }}"
                    dashBoardData.credit_amount="${mainViewModel.allData.filter { a->a.amount>0 }.sumOf{a->a.amount}}"
                    dashBoardData.debit_amount="${mainViewModel.allData.filter { a->a.amount<0 }.sumOf{a->a.amount}}"
                }
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

}