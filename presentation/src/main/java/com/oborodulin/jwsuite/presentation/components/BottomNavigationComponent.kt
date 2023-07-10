package com.oborodulin.jwsuite.presentation.components

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import timber.log.Timber

private const val TAG = "Presentation.BottomNavBarComponent"

@Composable
fun BottomNavigationComponent(modifier: Modifier, appState: AppState) {
    Timber.tag(TAG).d("BottomNavigationBar(...) called")
    NavigationBar(
        modifier = Modifier.navigationBarsPadding()
        /*modifier
            .graphicsLayer {
                shape = RoundedCornerShape(
                    topStart = 20.dp,
                    topEnd = 20.dp
                )
                clip = true
            },
        containerColor = Color.Black, //colorResource(id = R.color.black), //MaterialTheme.colorScheme.background,
        contentColor = Color.White //MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.background)
         */
    ) {
        val navBackStackEntry by appState.navBarNavController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        NavRoutes.bottomNavBarRoutes().forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconResId),
                        tint = MaterialTheme.colorScheme.onSurface,
                        contentDescription = stringResource(item.titleResId)
                    )
                },
                label = {
                    Text(
                        text = stringResource(item.titleResId),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = false,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                //colors = NavigationBarItemDefaults.colors(selectedIconColor = SpeechRed,)
                //unselectedContentColor = Color.White.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = { appState.navigateToBottomBarRoute(item.route) }
            )
        }
    }
}
