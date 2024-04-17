package com.samseptiano.jetpackscrollimage.presentation.ui.component

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import com.samseptiano.jetpackscrollimage.R
import com.samseptiano.jetpackscrollimage.presentation.viewmodel.MainViewModel

/**
 * Created by samuel.septiano on 17/04/2024.
 */
@Composable
fun ImageLoaderFromURL(itemIndex: Int, mainVM: MainViewModel) {
    Image(
        bitmap = mainVM.getListOfPhotoBitmap()[itemIndex].asImageBitmap(),
        contentDescription = stringResource(id = R.string.image_description),
    )
}

