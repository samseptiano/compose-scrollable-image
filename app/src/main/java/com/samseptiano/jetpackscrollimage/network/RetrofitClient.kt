package com.samseptiano.jetpackscrollimage.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private lateinit var retrofit: Retrofit
    private const val BASE_URL = "https://api.unsplash.com/"
    private const val CLIENT_ID = "yIpi8hM48UZyahpTTa8gwCY5qvh8c792DRzVqc9Vu-4"

    private fun getInstance(): Retrofit {
        if (!::retrofit.isInitialized) {
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit
    }

    internal fun create() : ApiClient {
        return getInstance().create(ApiClient::class.java)
    }

    internal fun getClientId() = CLIENT_ID
}