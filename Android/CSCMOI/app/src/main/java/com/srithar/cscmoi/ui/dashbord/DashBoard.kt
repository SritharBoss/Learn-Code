package com.srithar.cscmoi.ui.dashbord

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.CreditCardOff
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.srithar.cscmoi.ui.common.CommonViewModel
import com.srithar.cscmoi.ui.main_screen.MainViewModel
import com.srithar.cscmoi.utils.Utils

@Composable
fun DashboardScreen() {

    val viewModel: DashBoardViewModel = hiltViewModel()
    val commonViewModel: CommonViewModel = hiltViewModel()
    val mainViewModel: MainViewModel = hiltViewModel()
    viewModel.getDashBoardData(commonViewModel,mainViewModel)

    val dashboardItems = listOf(
        DashboardItem("₹ ${viewModel.dashBoardData.one_week_amount}", "Last Week", Icons.Default.TrendingUp),
        DashboardItem("₹ ${viewModel.dashBoardData.one_month_amount}", "Last Month", Icons.Default.TrendingUp),
        DashboardItem(viewModel.dashBoardData.customer_count, "All Customers", Icons.Default.People),
        DashboardItem(viewModel.dashBoardData.active_customers, "Active Customers", Icons.Default.Person),
        DashboardItem("₹ ${Utils.getIndianCurrencyFormat(viewModel.dashBoardData.credit_amount)}", "Credit", Icons.Default.CreditCard),
        DashboardItem("₹ ${Utils.getIndianCurrencyFormat(viewModel.dashBoardData.debit_amount)}", "Debit", Icons.Default.CreditCardOff)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(dashboardItems) { item ->
            DashboardCard(item)
        }
    }
}

data class DashboardItem(
    val amount: String,
    val label: String,
    val icon: ImageVector
)

@Composable
fun DashboardCard(item: DashboardItem) {
    val commonViewModel: CommonViewModel = hiltViewModel()
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.fillMaxWidth().clickable(
            onClick = {
                if(item.label == "All Customers" || item.label == "Active Customers"){
                    commonViewModel.isDashBoard=false
                }
            }
        )
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(36.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.amount,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = item.label,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}
