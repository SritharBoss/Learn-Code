package com.example.wificompose

import android.content.Context
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.wificompose.ui.theme.WifiComposeTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.IOException
import java.net.Inet4Address
import java.net.Inet6Address
import java.net.InetAddress
import java.net.NetworkInterface
import java.net.SocketException
import java.net.URL
import java.util.Collections
import java.util.Objects
import kotlin.math.abs
import kotlin.math.log10
import kotlin.math.pow


private var viewModel=MyViewModel()

class MainActivity : ComponentActivity() {
    private lateinit var appContext: Context;
    private var handler = Handler(Looper.getMainLooper());

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContext = applicationContext
        setContent {
            WifiComposeTheme {
                viewModel.dist=getWifiDistance()
                viewModel.ipv4=getIPv4Address() ?: "N/A"
                viewModel.ipv6=getIPv6Address() ?: "N/A"
                MyApp()
            }
        }

    }

    override fun onStart(){
        super.onStart()
        val runnable = object : Runnable {
            override fun run() {
                viewModel.dist=getWifiDistance()
                viewModel.ipv4=getIPv4Address() ?: "N/A"
                viewModel.ipv6=getIPv6Address() ?: "N/A"
                handler.postDelayed(this, 1000)
            }
        }
        handler.post(runnable)
    }


    private fun getIPv4Address(): String? {
        try {
            val allNetworkInterfaces: List<NetworkInterface> =
                Collections.list(NetworkInterface.getNetworkInterfaces())
            for (networkInterface in allNetworkInterfaces) {
                if (!networkInterface.name.equals("wlan0", ignoreCase = true)) continue
                val allInetAddresses: List<InetAddress> =
                    Collections.list(networkInterface.inetAddresses)
                for (inetAddress in allInetAddresses) {
                    if (!inetAddress.isLoopbackAddress && inetAddress is Inet4Address) {
                        return inetAddress.getHostAddress()
                    }
                }
            }
        } catch (ex: SocketException) {
            Log.e("getIPv4Address()", ex.toString())
        }
        return null
    }

    private fun getIPv6Address(): String? {
        try {
            val allNetworkInterfaces: List<NetworkInterface> =
                Collections.list(NetworkInterface.getNetworkInterfaces())
            for (networkInterface in allNetworkInterfaces) {
                if (!networkInterface.name.equals("wlan0", ignoreCase = true)) continue
                val allInetAddresses: List<InetAddress> =
                    Collections.list(networkInterface.inetAddresses)
                for (inetAddr in allInetAddresses) {
                    if (!inetAddr.isLoopbackAddress && inetAddr is Inet6Address) {
                        val index = inetAddr.toString().indexOf("%")
                        return if (index != -1) {
                            Objects.requireNonNull(inetAddr.getHostAddress())
                                .substring(0, index - 1)
                        } else inetAddr.getHostAddress()
                    }
                }
            }
        } catch (ex: SocketException) {
            Log.e("getIPv6Address()", ex.toString())
        }

        return null
    }

    private fun getWifiDistance(): String {
        val wifiManager = appContext.getSystemService(WIFI_SERVICE) as WifiManager
        val wifiInfo: WifiInfo = wifiManager.getConnectionInfo()
        val freq = wifiInfo.frequency
        val rssi = wifiInfo.rssi
        return String.format("~%.1fm", freqRssiToDistance(freq, rssi))
    }

    private fun freqRssiToDistance(frequency: Int, rssi: Int): Double {
        return 10.0.pow((27.55 - 20 * log10(frequency.toDouble()) + abs(rssi)) / 20.0)
    }

}

fun getIp():String{
    return runBlocking {
        var sb = StringBuilder("N/A")
        try {
            withContext(Dispatchers.IO){
                val url = URL("https://ifconfig.me")
                val connection = url.openConnection()
                connection.setRequestProperty("User-Agent", "curl")
                val bis = BufferedInputStream(connection.getInputStream())
                var ch: Int
                sb = StringBuilder()
                while (bis.read().also { ch = it } != -1) {
                    sb.append(ch.toChar())
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return@runBlocking sb.toString()
    }
}

@Composable
fun MyApp() {
    Surface(modifier = Modifier.fillMaxHeight(), color = MaterialTheme.colorScheme.background) {
        Column {
            MyTopAppBar()
            InternetIPCard()
            InfoCard(label = "IPv4", viewModel.ipv4)
            InfoCard(label = "IPv6", viewModel.ipv6)
            InfoCard(label = "Wifi Distance", viewModel.dist)
        }
    }
}


@Composable
fun InternetIPCard() {
    var ip by rememberSaveable {mutableStateOf("N/A")}
    Card(modifier = Modifier.padding(5.dp)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(
                modifier = Modifier
                    .padding(5.dp)
            ) {
                Text(
                    modifier = Modifier.padding(start = 5.dp, top = 5.dp),
                    text = "Internet IP Address",
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif
                )
                Text(
                    text = ip,
                    modifier = Modifier.padding(start = 5.dp, top = 5.dp, bottom = 5.dp),
                    fontSize = 18.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }

            Button(modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(end = 5.dp),
                onClick = { ip = getIp() }) {
                Text(text = "Fetch IP")
            }
        }

    }
}

@Composable
fun InfoCard(label: String = "Label", myVal: String="Value") {
    Card(modifier = Modifier.padding(5.dp)) {
        Row(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(modifier = Modifier.padding(start = 5.dp), text = label)
            Text(text = myVal)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
//@Preview
@Composable
fun MyTopAppBar() {
    TopAppBar(title = { Text(text = "Wifi Network Info") })
}

data class Person(val name: String, val age: Int)

@Composable
fun Test() {
    val persons = listOf(
        Person("Srithar", 25),
        Person("Sriram", 23),
        Person("Srithar", 25),
        Person("Sriram", 23),
        Person("Srithar", 25),
        Person("Sriram", 23),
        Person("Srithar", 25),
        Person("Sriram", 23)
    )
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(items = persons) { person ->
            var isExpanded by rememberSaveable { mutableStateOf(false) }
            val padding = if (isExpanded) 48.dp else 0.dp
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(25.dp, 0.dp)
                    .padding(bottom = padding),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = person.name, fontSize = 25.sp)
                Text(text = person.age.toString(), fontSize = 25.sp)
                Button(onClick = { isExpanded = !isExpanded }) {
                    Icon(imageVector = (Icons.Filled.AccountCircle), contentDescription = "")
                    Text(if (!isExpanded) "Add Padding Below" else "Remove Padding")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Test()
}