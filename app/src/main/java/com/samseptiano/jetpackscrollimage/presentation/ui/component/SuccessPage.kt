package com.samseptiano.jetpackscrollimage.presentation.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.samseptiano.jetpackscrollimage.model.data.Photos
import com.samseptiano.jetpackscrollimage.presentation.viewmodel.MainViewModel

/**
 * Created by samuel.septiano on 17/04/2024.
 */
@Composable
fun SuccessPage(
    mainVM: MainViewModel,
    totalListPhoto: List<Photos>,
    gridState: LazyGridState,
    columnState: LazyListState
) {
    val density = LocalDensity.current
    var columnHeightDp by remember { mutableStateOf(0.dp) }

    LazyColumn(state = columnState) {
        item {
            Box(
                modifier = Modifier.heightIn(
                    0.dp,
                    LocalConfiguration.current.screenHeightDp.dp - columnHeightDp
                )
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = gridState
                ) {
                    items(totalListPhoto.size) { index ->
                        Box(
                            modifier = Modifier
                                .padding(5.dp)
                                .height(200.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            ImageLoaderFromURL(index, mainVM)
                        }
                    }
                }
            }
        }
        item {
            LoadMoreProgressbar(gridState, mainVM, onButtonHeightGet = {
                with(density) {
                    columnHeightDp = it.toDp()
                }
            })
        }
    }
}
