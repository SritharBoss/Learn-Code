package com.srithar.cscmoi.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class Utils{
    companion object{

        var sdf=SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        var sdf1=SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        fun getTodayDateString():String{
            return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(System.currentTimeMillis()))
        }

        fun parseDDMMYYYYtoYYYYMMDD(inputString: String):String{
            return SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(sdf.parse(inputString)?:"")
        }

        fun parseYYYYMMDD(inputString: String):String{
            return sdf1.format(inputString)?:""
        }

        fun parseYYYYMMDDtoDate(inputString: String):Date{
            return sdf1.parse(inputString)?:Date()
        }

        fun updateAmount(isCr: Boolean, amount: String):String {
            if(isCr){
                if((amount.toIntOrNull()?:0)<0){
                    return ""+((amount.toIntOrNull()?:0)*-1)
                }
            }else{
                if((amount.toIntOrNull()?:0)>0){
                    return ""+((amount.toIntOrNull()?:0)*-1)
                }
            }
            return amount
        }

        fun getIndianCurrencyFormat(amount: String): String {
            val stringBuilder = StringBuilder()
            val amountArray: CharArray? = amount.toCharArray()
            var a = 0
            var b = 0
            for (i in amountArray!!.indices.reversed()) {
                if (a < 3) {
                    stringBuilder.append(amountArray[i])
                    a++
                } else if (b < 2) {
                    if (b == 0) {
                        if(!(amount.startsWith("-") &&  i==0)){
                            stringBuilder.append(",")
                        }
                        stringBuilder.append(amountArray[i])
                        b++

                    } else {
                        stringBuilder.append(amountArray[i])
                        b = 0
                    }
                }
            }
            return stringBuilder.reverse().toString()
        }
    }

    fun add(a:Int,b:Int): Int{
        return a+b;
    }
}
