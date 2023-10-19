package com.oborodulin.jwsuite.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.oborodulin.home.common.ui.components.IconComponent
import com.oborodulin.home.common.ui.theme.Typography
import com.oborodulin.jwsuite.presentation.ui.AppState
import timber.log.Timber

private const val TAG = "Presentation.ScaffoldComponent"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldComponent(
    appState: AppState,
//    scaffoldState: ScaffoldState = rememberScaffoldState(),
    nestedScrollConnection: NestedScrollConnection? = null,
    isNestedScrollConnection: Boolean = false,
    @StringRes topBarTitleResId: Int? = null,
    topBarTitle: String = "",
    actionBar: @Composable (() -> Unit)? = null,
    topBarNavImageVector: ImageVector? = null,
    @DrawableRes topBarNavPainterResId: Int? = null,
    @StringRes topBarNavCntDescResId: Int? = null,
    onTopBarNavClick: () -> Unit = {},
    topBarActions: @Composable RowScope.() -> Unit = {},
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: @Composable () -> Unit = {},
    isBottomNavigation: Boolean = false,
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    content: @Composable (PaddingValues) -> Unit
) {
    Timber.tag(TAG).d("ScaffoldComponent() called")
    //val context = LocalContext.current
    var actionBarTitle by rememberSaveable { mutableStateOf(appState.appName) }

    /*    LaunchedEffect(appState.navBarNavController) {
            appState.navBarNavController.currentBackStackEntryFlow.collect { backStackEntry ->
                // You can map the title based on the route using:
                backStackEntry.destination.route?.let {
                    actionBarTitle = appState.appName + " :: " + NavRoutes.titleByRoute(context, it)
                }
            }
        }

     */
    val modifier = Modifier.fillMaxSize()
    Scaffold(
        modifier = nestedScrollConnection?.let {
            if (isNestedScrollConnection) modifier.nestedScroll(it) else null
        } ?: modifier,
        topBar = {
            when (topBar) {
                null ->
                    TopAppBar(
                        title = {
                            Column(
                                verticalArrangement = Arrangement.Center
                            ) {
                                if (actionBar == null) {
                                    Text(
                                        text = appState.appName.plus(topBarTitleResId?.let {
                                            " - ${stringResource(it)}"
                                        }
                                            ?: (if (topBarTitle.isNotEmpty()) " - $topBarTitle" else "")),
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                                if (appState.actionBarSubtitle.value.isNotEmpty()) {
                                    Text(
                                        text = appState.actionBarSubtitle.value,
                                        style = Typography.titleMedium,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                                actionBar?.let { it() }
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = onTopBarNavClick) {
                                IconComponent(
                                    imageVector = topBarNavImageVector,
                                    painterResId = topBarNavPainterResId,
                                    contentDescriptionResId = topBarNavCntDescResId
                                )
                            }
                            /*when (topBarNavigationIcon) {
                                null -> IconButton(onClick = { context.toast("Menu button clicked...") }) {
                                    Icon(Icons.Filled.Menu, null)
                                }

                                else -> topBarNavigationIcon()
                            }*/
                        },
                        actions = topBarActions
                    )

                else -> topBar()
            }
        },
        floatingActionButtonPosition = floatingActionButtonPosition,
        floatingActionButton = floatingActionButton,
        bottomBar = { if (isBottomNavigation) bottomBar }
    ) {
        content(it)
    }
}