package com.yu.my_note_app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun CustomizedPager(modifier: Modifier = Modifier,
                    pages: List<@Composable () -> Unit>,
                    pagerState: PagerState = rememberPagerState(pageCount = { pages.size })){
    Column(modifier) {
        if (pages.count() > 1)
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 3.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount){page ->
                    val col = if (pagerState.currentPage == page) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.tertiary
                    Box(Modifier
                        .padding(horizontal = 6.dp, vertical = 4.dp)
                        .clip(CircleShape)
                        .background(col)
                        .size(10.dp)) { }
                }
            }

        HorizontalPager(
            pagerState,
            pageSpacing = 16.dp,
            beyondViewportPageCount = 1,
        ) {
             page ->
                pages[page]()

        }
    }

}