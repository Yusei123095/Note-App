package com.yu.my_note_app.ui.home.addnote

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.time.LocalTime

@Composable
fun TimeWheel(
    timeState: LocalTime,
    onChange: (hour: Int, minute: Int) -> Unit
) {
    var hour by remember { mutableIntStateOf(timeState.hour) }
    var minute by remember { mutableIntStateOf(timeState.minute) }

    Row(
        modifier = Modifier.fillMaxWidth().padding(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        WheelPicker(
            items = (0..23).map { "%02d".format(it) },
            selectedIndex = timeState.hour,
            onSelected = {
                hour = it; onChange(hour, minute)
            },
            modifier = Modifier.width(80.dp)
        )
        Text(":", style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 8.dp))
        WheelPicker(
            items = (0..59).map { "%02d".format(it) },
            selectedIndex = timeState.minute,
            onSelected = {
                minute = it; onChange(hour, minute)
            },
            modifier = Modifier.width(80.dp)
        )
    }
}

@Composable
fun WheelPicker(
    items: List<String>,
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    visibleCount: Int = 5,
    itemHeight: Dp = 44.dp
) {
    val pad = visibleCount / 2
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = selectedIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    val itemHeightPx = with(LocalDensity.current) { itemHeight.toPx() }


    val selected by remember {
        derivedStateOf {
            val i = listState.firstVisibleItemIndex
            val next = listState.firstVisibleItemScrollOffset > itemHeightPx / 2
            (if (next) i + 1 else i).coerceIn(0, items.lastIndex)
        }
    }

    LaunchedEffect(selected) { onSelected(selected) }
    LaunchedEffect(selectedIndex) {
        if (!listState.isScrollInProgress && selectedIndex != selected) {
            listState.animateScrollToItem(selectedIndex)
        }
    }

    Box(modifier.height(itemHeight * visibleCount), contentAlignment = Alignment.Center) {

        Box(
            Modifier
                .fillMaxWidth()
                .height(itemHeight)
                .background(
                    MaterialTheme.colorScheme.surfaceContainerHighest,
                    MaterialTheme.shapes.medium
                )
        )
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            items(pad) { Spacer(Modifier.height(itemHeight)) }   // 上の余白
            itemsIndexed(items) { index, text ->
                val isSelected = index == selected
                Box(Modifier.height(itemHeight), contentAlignment = Alignment.Center) {
                    Text(
                        text,
                        style = MaterialTheme.typography.headlineSmall,
                        color = if (isSelected) MaterialTheme.colorScheme.onSurface
                        else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                        fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
                    )
                }
            }
            items(pad) { Spacer(Modifier.height(itemHeight)) }   // 下の余白
        }
    }
}