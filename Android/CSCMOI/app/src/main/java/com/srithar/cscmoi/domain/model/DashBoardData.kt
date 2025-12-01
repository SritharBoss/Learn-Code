package com.srithar.cscmoi.domain.model

data class DashBoardData(
    var credit_amount: String="-",
    var customer_count:String="-",
    var active_customers:String="-",
    var debit_amount:String="-",
    var grand_total:String="-",
    var one_month_amount:String="-",
    var one_week_amount:String="-"
)