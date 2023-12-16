package com.oborodulin.jwsuite.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.oborodulin.home.common.ui.components.IconComponent
import com.oborodulin.home.common.ui.theme.Typography
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import timber.log.Timber

private const val TAG = "Presentation.ScaffoldComponent"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldComponent(
    nestedScrollConnection: NestedScrollConnection? = null,
    isNestedScrollConnection: Boolean = false,
    @StringRes topBarTitleResId: Int? = null,
    topBarTitle: String? = null,
    topBarSubtitle: String? = null,
    actionBar: @Composable (() -> Unit)? = null,
    topBarNavImageVector: ImageVector? = Icons.Outlined.ArrowBack,
    @DrawableRes topBarNavPainterResId: Int? = null,
    @StringRes topBarNavCntDescResId: Int? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    defTopBarActions: @Composable RowScope.() -> Unit = {},
    topBarActions: @Composable RowScope.() -> Unit = {},
    isActionsLeading: Boolean = true,
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    content: @Composable (PaddingValues) -> Unit
) {
    Timber.tag(TAG).d("ScaffoldComponent(...) called")
    val appState = LocalAppState.current
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
                                        text = topBarTitle ?: //LocalAppState.current.appName.plus(
                                        topBarTitleResId?.let {
                                            stringResource(it) // " - ${stringResource(it)}"
                                        }.orEmpty(),
                                        fontWeight = FontWeight.Bold,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                    topBarSubtitle?.let {
                                        Text(
                                            text = it,
                                            style = Typography.titleMedium,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    //if (appState.actionBarSubtitle.value.isNotEmpty()) {}
                                }
                                actionBar?.let { it() }
                            }
                        },
                        navigationIcon = navigationIcon?.let { { it() } } ?: {
                            // check icons for auth screens: Signup and Login
                            if (topBarNavImageVector != null || topBarNavPainterResId != null) {
                                topBarNavImageVector?.let {
                                    IconButton(onClick = appState.handleTopBarNavClick.value) { // onTopBarNavClick
                                        IconComponent(
                                            imageVector = it,
                                            painterResId = topBarNavPainterResId,
                                            contentDescriptionResId = topBarNavCntDescResId
                                        )
                                    }
                                }
                            }
                            /*when (topBarNavigationIcon) {
                                null -> IconButton(onClick = { context.toast("Menu button clicked...") }) {
                                    Icon(Icons.Filled.Menu, null)
                                }

                                else -> topBarNavigationIcon()
                            }*/
                        },
                        actions = {
                            if (isActionsLeading) {
                                topBarActions(); defTopBarActions()
                            } else {
                                defTopBarActions(); topBarActions()
                            }
                        }
                    )

                else -> topBar()
            }
        },
        floatingActionButtonPosition = floatingActionButtonPosition,
        floatingActionButton = floatingActionButton,
        bottomBar = bottomBar
    ) {
        content(it)
    }
}