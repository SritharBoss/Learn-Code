package com.srithar.cscmoi.data.network.service

import com.srithar.cscmoi.data.network.model.LoginRequest
import com.srithar.cscmoi.data.network.model.LoginResponse
import com.srithar.cscmoi.data.network.model.TransactionRequest
import com.srithar.cscmoi.data.network.model.TransactionResponse
import com.srithar.cscmoi.domain.model.Customer
import com.srithar.cscmoi.domain.model.CustomerRequest
import com.srithar.cscmoi.domain.model.DashBoardData
import com.srithar.cscmoi.domain.model.TransactionData
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("api/customers")
    suspend fun getCustomers(): List<Customer>

    @POST("api/client/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("api/transactions/{id}")
    suspend fun getTransactions(@Path("id") id: String): List<TransactionData>

    @GET("api/transactions")
    suspend fun getTransactions(): List<TransactionData>

    @GET("api/dashboard")
    suspend fun getDashBoardData(): List<DashBoardData>

    @GET("api/transaction-types")
    suspend fun getTransactionTypes(): List<String>

    @GET("api/customers/villages")
    suspend fun getVillages(): VillageResponse

    @PUT("api/transactions")
    suspend fun createTransaction(@Body request: TransactionRequest): TransactionResponse

    @POST("api/customers")
    suspend fun createCustomer(@Body request: CustomerRequest): TransactionResponse

    @PUT("api/transactions/update/{id}")
    suspend fun editTransaction(@Path("id") id: String,@Body request: TransactionRequest): TransactionResponse

    @PUT("api/customers/{id}")
    suspend fun editCustomer(@Path("id") id: String,@Body request: CustomerRequest): TransactionResponse

    @DELETE("api/transactions/delete/{id}")
    suspend fun deleteTransaction(@Path("id") id: String): TransactionResponse

}

data class VillageResponse(val villages: List<String>)

