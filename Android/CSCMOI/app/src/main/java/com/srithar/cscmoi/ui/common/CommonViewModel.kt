package com.srithar.cscmoi.ui.common

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CommonViewModel @Inject constructor() : ViewModel() {

    var isLoggedIn by mutableStateOf(false)
    var isServerReachable by mutableStateOf(false)
    var errorMessage by mutableStateOf("")
    var isDashBoard by mutableStateOf(true)
    var isDarkTheme by mutableStateOf(false)

}