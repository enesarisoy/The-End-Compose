package com.ns.theendcompose.ui.components.sections

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.ns.theendcompose.data.model.Presentable
import com.ns.theendcompose.data.model.PresentableItemState
import com.ns.theendcompose.ui.components.items.PresentableItem
import com.ns.theendcompose.ui.components.texts.SectionLabel
import com.ns.theendcompose.ui.theme.spacing

@Composable
fun PresentableListSection(
    title: String,
    list: List<Presentable>,
    modifier: Modifier = Modifier,
    selectedId: Int? = null,
    onPresentableClick: (Int) -> Unit = {}
) {
    val lazyListState = rememberLazyListState()

    LaunchedEffect(selectedId){
        val itemIndex = list.indexOfFirst { presentable -> presentable.id == selectedId}

        if (itemIndex >= 0){
            lazyListState.scrollToItem(itemIndex)
        }
    }
    if (list.isNotEmpty()){
        Column(modifier = modifier) {
            SectionLabel(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = MaterialTheme.spacing.medium),
                text = title
            )
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacing.small),
                state = lazyListState,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.medium)
            ) {
                items(list) { presentable ->
                    PresentableItem(
                        presentableState = PresentableItemState.Result(presentable),
                        selected = selectedId == presentable.id,
                        onClick = { onPresentableClick(presentable.id) }
                    )
                }
            }
        }
    }
}