package com.srithar.cscmoi.data.network.client

import android.content.Context
import com.srithar.cscmoi.data.network.service.ApiService
import com.srithar.cscmoi.data.pref.PreferenceManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var token: String? = null
    private lateinit var baseUrl: String

    fun setBaseUrl(context: Context) {
        baseUrl = PreferenceManager.getBaseUrl(context)
    }

    fun setToken(newToken: String) {
        token = newToken
    }

    private val authInterceptor = Interceptor { chain ->
        val original = chain.request()
        val builder = original.newBuilder()

        token?.let {
            builder.addHeader("Authorization", "Bearer $it")
        }

        chain.proceed(builder.build())
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}