package com.samseptiano.jetpackscrollimage.network

import com.samseptiano.jetpackscrollimage.model.data.PhotoResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiClient {
    @GET("photos")
    suspend fun getAllPhotos(
        @Query("client_id") clientId: String = RetrofitClient.getClientId(),
        @Query("page") page: Int = 1,
        @Query("per_page") itemPage: Int = 10,
        @Query("orientation") orientation: String = "portrait"
    ): PhotoResponse
}