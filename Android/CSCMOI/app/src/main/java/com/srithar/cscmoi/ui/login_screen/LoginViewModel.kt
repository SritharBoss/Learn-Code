package com.srithar.cscmoi.ui.login_screen

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.srithar.cscmoi.data.network.client.RetrofitClient
import com.srithar.cscmoi.data.network.model.LoginRequest
import com.srithar.cscmoi.data.network.model.LoginResponse
import com.srithar.cscmoi.data.pref.PreferenceManager
import com.srithar.cscmoi.ui.common.CommonViewModel
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel(){
    var loginError by mutableStateOf<String?>(null)
    var isLoading by mutableStateOf(false)

    fun login(commonViewModel: CommonViewModel, email: String, password: String, context: Context) {

        viewModelScope.launch {
            isLoading = true
            loginError = null

            try {
                val response: LoginResponse = RetrofitClient.api.login(
                    LoginRequest(
                        email,
                        password
                    )
                )
                if(response.token==null || response.token.trim()==""){
                    throw Exception("$response")
                }
                RetrofitClient.setToken(response.token)
                commonViewModel.isLoggedIn = true
                commonViewModel.isServerReachable=true
                PreferenceManager.saveCredentials(context, email, password)

            } catch (e: Exception) {
                // Try offline auth

                Toast.makeText(context,"Server Login Error :\n"+e.message,Toast.LENGTH_LONG).show()

                if (PreferenceManager.validateOfflineLogin(context, email, password)) {
                    commonViewModel.isLoggedIn = true
                    commonViewModel.isDashBoard = false
                } else {
                    loginError = "Login failed: ${e.message}"
                }
            } finally {
                isLoading = false
            }
        }
    }

}