package com.oborodulin.jwsuite.presentation.components

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.compose.currentBackStackEntryAsState
import com.oborodulin.home.common.ui.components.IconComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import timber.log.Timber

private const val TAG = "Presentation.BottomNavBarComponent"

@Composable
fun BottomNavigationComponent(modifier: Modifier) {
    Timber.tag(TAG).d("BottomNavigationBar(...) called")
    val appState = LocalAppState.current
    NavigationBar(
        modifier = Modifier
            .navigationBarsPadding()
            .then(modifier)
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
        val navBackStackEntry by appState.barNavController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        NavRoutes.bottomNavBarRoutes().forEach { item ->
            val label = stringResource(item.titleResId)
            NavigationBarItem(
                icon = {
                    IconComponent(
                        imageVector = item.iconImageVector,
                        painterResId = item.iconPainterResId,
                        contentDescriptionResId = item.titleResId,
                        //tint = MaterialTheme.colorScheme.onSurface,
                    )
                },
                label = {
                    Text(
                        text = label,
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
                onClick = {
                    appState.navigateToBarRoute(item.route)
                    appState.actionBarTitle.value = label
                }
            )
        }
    }
}
