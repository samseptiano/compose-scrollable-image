package com.samseptiano.jetpackscrollimage.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.lifecycleScope
import com.samseptiano.jetpackscrollimage.model.data.PhotoResponse
import com.samseptiano.jetpackscrollimage.model.data.Photos
import com.samseptiano.jetpackscrollimage.model.data.state.CommonState
import com.samseptiano.jetpackscrollimage.model.data.state.Status
import com.samseptiano.jetpackscrollimage.presentation.ui.component.FailedLoadWithToast
import com.samseptiano.jetpackscrollimage.presentation.ui.component.FailedPage
import com.samseptiano.jetpackscrollimage.presentation.ui.component.Loading
import com.samseptiano.jetpackscrollimage.presentation.ui.component.ShowLoadSuccessScreenOnly
import com.samseptiano.jetpackscrollimage.presentation.ui.component.SuccessPage
import com.samseptiano.jetpackscrollimage.presentation.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    private val mainVM by lazy { MainViewModel(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launchWhenStarted {
            mainVM.getAllPhotos()
            mainVM.getCurrentState().collect {
                setContent {
                    AppState(it, mainVM)
                }
            }
        }
    }
}

@Composable
fun AppState(state: CommonState<PhotoResponse>, mainVM: MainViewModel) {
    val totalListPhoto by rememberSaveable { mutableStateOf(arrayListOf<Photos>()) }
    val gridState = rememberLazyGridState()
    val columnState = rememberLazyListState()

    when (state.status) {
        Status.LOADING -> {
            if (mainVM.getCurrentPage() > 1 && totalListPhoto.isNotEmpty()) {
                ShowLoadSuccessScreenOnly(mainVM, totalListPhoto, gridState, columnState)
            } else {
                Loading()
            }
        }
        Status.FAILED -> {
            if (mainVM.getCurrentPage() > 1) {
                FailedLoadWithToast(mainVM, totalListPhoto, gridState, columnState)
            } else {
                FailedPage(mainVM)
            }
        }
        Status.SUCCESS -> {
            state.data?.map { photo -> totalListPhoto.add(photo) }
            SuccessPage(mainVM, totalListPhoto, gridState, columnState)
        }
    }
}










