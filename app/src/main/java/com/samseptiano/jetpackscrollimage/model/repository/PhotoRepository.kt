package com.samseptiano.jetpackscrollimage.model.repository

import com.samseptiano.jetpackscrollimage.model.data.PhotoResponse
import com.samseptiano.jetpackscrollimage.network.ApiClient
import com.samseptiano.jetpackscrollimage.model.data.state.CommonState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class PhotoRepository(private val apiService: ApiClient) {
    suspend fun getPhoto(page: Int, itemPage: Int): Flow<CommonState<PhotoResponse>> {
        return flow {
            val photos = apiService.getAllPhotos(page = page, itemPage = itemPage)
            emit(CommonState.success(photos))
        }.flowOn(Dispatchers.IO)
    }
}
