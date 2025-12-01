package com.srithar.cscmoi.app.host

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.srithar.cscmoi.data.network.client.RetrofitClient
import com.srithar.cscmoi.ui.common.CommonViewModel
import com.srithar.cscmoi.ui.common.ConfirmationDialog
import com.srithar.cscmoi.ui.common.ErrorAlertDialog
import com.srithar.cscmoi.ui.login_screen.LoginScreen
import com.srithar.cscmoi.ui.main_screen.MainScreenWithApi
import com.srithar.cscmoi.ui.theme.CSCMOITheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent{
            AppContent()
        }
    }
}

@Composable
fun AppContent(){
    val viewModel: CommonViewModel= hiltViewModel()
    CSCMOITheme(darkTheme = viewModel.isDarkTheme) {
        AppMainScreen()

        var showDialog by remember { mutableStateOf(false) }

        // Handle back press
        BackHandler {
            showDialog = true
        }

        val viewModel: CommonViewModel= hiltViewModel()

        val context = LocalContext.current
        val activity = context as? Activity
        if(viewModel.isLoggedIn && showDialog){
            ConfirmationDialog(title = "Confirm Logout", message = "Are you sure you want to Logout?", onConfirm = {
                showDialog=false
                viewModel.isLoggedIn=false
                RetrofitClient.setToken("")
            }, onDismiss = {showDialog=false})
        }else if(!viewModel.isLoggedIn && showDialog){
            activity?.finish()
        }
    }
}

@Composable
fun AppMainScreen() {
    val commonViewModel: CommonViewModel= hiltViewModel()
    if (!commonViewModel.isLoggedIn) {
        LoginScreen( LocalContext.current)
    } else {
        MainScreenWithApi()
    }

    if(commonViewModel.errorMessage.isNotEmpty()){
        ErrorAlertDialog(commonViewModel) { commonViewModel.errorMessage = "" }
    }

}