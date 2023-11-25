package com.example.wificompose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class MyViewModel:ViewModel() {
    var dist by mutableStateOf("N/A")
    var ipv4 by mutableStateOf("N/A")
    var ipv6 by mutableStateOf("N/A")
}