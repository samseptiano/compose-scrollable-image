package com.samseptiano.jetpackscrollimage.presentation.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.samseptiano.jetpackscrollimage.R
import com.samseptiano.jetpackscrollimage.presentation.viewmodel.MainViewModel

/**
 * Created by samuel.septiano on 17/04/2024.
 */
@Composable
fun Loading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .height(100.dp)
                .width(100.dp),
            backgroundColor = colorResource(R.color.white)
        ) {
            CircularProgressIndicator(
                strokeWidth = 3.dp,
                modifier = Modifier.padding(18.dp),
                color = colorResource(R.color.white)
            )
        }
    }
}

@Composable
fun LoadMoreProgressbar(
    gridState: LazyGridState,
    mainVM: MainViewModel,
    onButtonHeightGet: (Int) -> Unit
) {
    if (!gridState.isScrollingUp()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .onGloballyPositioned { coordinates ->
                    onButtonHeightGet(coordinates.size.height)
                },
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                strokeWidth = 3.dp,
                modifier = Modifier.padding(18.dp),
                color = colorResource(R.color.white)
            )
        }
        mainVM.getAllPhotos(mainVM.getCurrentPage())
    }
}


@Composable
fun LazyGridState.isScrollingUp(): Boolean {
    val offset by remember(this) { mutableIntStateOf(firstVisibleItemScrollOffset) }
    return remember(this) { derivedStateOf { (firstVisibleItemScrollOffset - offset) < 0 } }.value
}