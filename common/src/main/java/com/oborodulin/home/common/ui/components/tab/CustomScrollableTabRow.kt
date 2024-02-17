package com.oborodulin.home.common.ui.components.tab

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.ui.theme.Typography
import com.oborodulin.home.common.util.textWidthMatchedTabIndicatorOffset
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomScrollableTabRow(tabRowItems: List<TabRowItem>, userRoles: List<String> = emptyList()) {
    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    // https://medium.com/@sukhdip_sandhu/jetpack-compose-scrollabletabrow-indicator-matches-width-of-text-e79c0e5826fe
    // https://stackoverflow.com/questions/70923243/how-to-adjust-tabrow-indicator-width-according-to-the-text-above-it
    val density = LocalDensity.current
    val tabs = tabRowItems.filter { tab ->
        userRoles.isEmpty() || tab.userRoles.isEmpty() || tab.userRoles.any { role ->
            userRoles.contains(role)
        }
    }
    val tabWidths = remember {
        val tabWidthStateList = mutableStateListOf<Dp>()
        repeat(tabs.size) {
            tabWidthStateList.add(0.dp)
        }
        tabWidthStateList
    }
    // https://stackoverflow.com/questions/65581582/scrollabletabrow-indicator-width-to-match-text-inside-tab
    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        //modifier = Modifier.padding(8.dp).height(50.dp),
        edgePadding = 0.dp,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.textWidthMatchedTabIndicatorOffset(
                    currentTabPosition = tabPositions[pagerState.currentPage],
                    tabWidth = tabWidths[pagerState.currentPage]
                )
            )
        }
    ) {
        tabs.forEachIndexed { tabIndex, tab ->
            Tab(
                //modifier = Modifier.height(50.dp),
                selected = pagerState.currentPage == tabIndex,
                onClick = {
                    tab.onClick()
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(tabIndex)
                    }
                },
                icon = tab.icon?.let { icon ->
                    { Icon(imageVector = icon, contentDescription = "") }
                },
                text = {
                    Text(
                        text = tab.title,
                        style = if (pagerState.currentPage == tabIndex) Typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold
                        ) else Typography.bodyLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        onTextLayout = { textLayoutResult ->
                            tabWidths[tabIndex] =
                                with(density) { textLayoutResult.size.width.toDp() }
                        }
                    )
                }
            )
        }
    }
    HorizontalPager(
        pageCount = tabs.size,
        state = pagerState,
    ) { tabs[pagerState.currentPage].view() }
}