package com.srithar.cscmoi.ui.login_screen

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.srithar.cscmoi.R
import com.srithar.cscmoi.data.network.client.RetrofitClient
import com.srithar.cscmoi.data.pref.PreferenceManager
import com.srithar.cscmoi.ui.common.CommonViewModel

@Composable
fun LoginScreen(context: Context) {

    val viewModel: LoginViewModel= hiltViewModel()
    val commonViewModel: CommonViewModel= hiltViewModel()

    var username by remember { mutableStateOf("cscpvl2014@gmail.com") }
    var password by remember { mutableStateOf("12345678") }
    var passwordVisible by remember { mutableStateOf(false) }

    var showSettings by remember { mutableStateOf(false) }
    var baseUrl by remember { mutableStateOf(PreferenceManager.getBaseUrl(context)) }

    val isLoading = viewModel.isLoading
    val error = viewModel.loginError

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(id = R.drawable.bg_login),
            contentDescription = "Login background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // ⚙️ Settings Button Top Right
        IconButton (
            onClick = { showSettings = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(32.dp)
        ) {
            Icon(Icons.Default.Settings, contentDescription = "Settings")
        }

        if (showSettings) {
            AlertDialog(
                onDismissRequest = { showSettings = false },
                confirmButton = {
                    TextButton (onClick = {
                        PreferenceManager.setBaseUrl(context, baseUrl)
                        RetrofitClient.setBaseUrl(context)
                        showSettings = false
                    }) {
                        Text("Save")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showSettings = false }) {
                        Text("Cancel")
                    }
                },
                title = { Text("Edit Host Name") },
                text = {
                    OutlinedTextField(
                        value = baseUrl,
                        onValueChange = { baseUrl = it },
                        label = { Text("http://192.168.1.109:3001") },
                        singleLine = true
                    )
                }
            )
        }

        RetrofitClient.setBaseUrl(context)

        // Optional: Soft scrim for readability
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.6f))
        )

        // 🎯 Login Card Centered
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(0.98f)
                    .wrapContentHeight(),
                elevation = CardDefaults.cardElevation(8.dp),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceDim)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("\uD83D\uDD11 MOI Login", style = MaterialTheme.typography.headlineSmall)

                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (passwordVisible)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff

                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = image, contentDescription = if (passwordVisible) "Hide password" else "Show password")
                            }
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = { viewModel.login(commonViewModel,username, password, context ) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor = MaterialTheme.colorScheme.primary,
                            disabledContentColor = MaterialTheme.colorScheme.onPrimary
                        ),
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isLoading
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = MaterialTheme.colorScheme.onPrimary,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Login")
                        }
                    }

                    error?.let {
                        Text(
                            it,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}