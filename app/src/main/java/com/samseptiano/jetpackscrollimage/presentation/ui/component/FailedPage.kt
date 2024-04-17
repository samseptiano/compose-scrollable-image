package com.samseptiano.jetpackscrollimage.presentation.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samseptiano.jetpackscrollimage.R
import com.samseptiano.jetpackscrollimage.model.data.Photos
import com.samseptiano.jetpackscrollimage.presentation.viewmodel.MainViewModel
import com.samseptiano.jetpackscrollimage.util.CommonUtil.showToast

/**
 * Created by samuel.septiano on 17/04/2024.
 */
@Composable
fun FailedPage(mainVM: MainViewModel) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(stringResource(id = R.string.unable_to_load_more), fontSize = 20.sp)
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(R.color.black),
                    contentColor = colorResource(R.color.white)
                ),
                onClick = {
                    mainVM.getAllPhotos(mainVM.getCurrentPage())
                }) {
                Text(stringResource(id = R.string.refresh_data))
            }
        }
    }
}

@Composable
fun FailedLoadWithToast(
    mainVM: MainViewModel,
    totalListPhoto: List<Photos>,
    gridState: LazyGridState,
    columnState: LazyListState
) {
    SuccessPage(mainVM, totalListPhoto, gridState, columnState)
    showToast(LocalContext.current, stringResource(id = R.string.unable_to_load_more))
}