package com.samseptiano.jetpackscrollimage.presentation.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samseptiano.jetpackscrollimage.model.data.PhotoResponse
import com.samseptiano.jetpackscrollimage.model.data.state.CommonState
import com.samseptiano.jetpackscrollimage.model.data.state.Status
import com.samseptiano.jetpackscrollimage.model.repository.PhotoRepository
import com.samseptiano.jetpackscrollimage.network.RetrofitClient
import com.samseptiano.jetpackscrollimage.util.BitmapCacheUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class MainViewModel(private var context: Context) : ViewModel() {
    private var _listOfPhotoBitmap = arrayListOf<Bitmap>()
    private var _currentPage by mutableIntStateOf(1)
    private val _repository = PhotoRepository(RetrofitClient.create())
    private val _currentState = MutableStateFlow(CommonState<PhotoResponse>(Status.LOADING, null, null))
    private val defaultMaxItem = 10
    private var lastIndexed = 0

    fun getCurrentState() = _currentState
    fun getCurrentPage() = _currentPage
    fun getListOfPhotoBitmap() = _listOfPhotoBitmap

    fun getAllPhotos(page: Int = 1) {
        _currentState.value = CommonState.loading()
        viewModelScope.launch {
            _repository.getPhoto(page, defaultMaxItem)
                .catch {
                    _currentState.value = CommonState.failed(it.message.toString())
                }
                .collect {
                    if(it.data.isNullOrEmpty()) {
                        _currentState.value = CommonState.failed("Response success but empty result")
                    } else {
                        it.data.mapIndexed { index, photo ->
                            if(_currentPage == 1) lastIndexed = index
                            val realIndex = if(_currentPage > 1) ++lastIndexed else index
                            val bitmapPhoto = BitmapCacheUtil().getLastBitmapCached(context,realIndex, photo.urls.regular, getListOfPhotoBitmap())

                            _listOfPhotoBitmap.add(bitmapPhoto.second!!)
                        }
                        _currentState.value = CommonState.success(it.data)
                        _currentPage += 1
                    }
                }
        }
    }


}
