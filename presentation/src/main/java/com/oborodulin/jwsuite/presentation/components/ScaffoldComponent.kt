package com.oborodulin.jwsuite.presentation.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
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
import androidx.compose.ui.unit.dp
import com.oborodulin.home.common.ui.components.IconComponent
import com.oborodulin.home.common.ui.components.bar.action.BarActionItem
import com.oborodulin.home.common.ui.theme.Typography
import com.oborodulin.jwsuite.domain.types.MemberRoleType
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import timber.log.Timber

private const val TAG = "Presentation.ScaffoldComponent"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldComponent(
    nestedScrollConnection: NestedScrollConnection? = null,
    isNestedScrollConnection: Boolean = false,
    @StringRes topBarTitleResId: Int? = null,
    navRoute: NavRoutes? = null,
    topBarTitle: String? = null,
    topBarSubtitle: String? = null,
    actionBar: @Composable (() -> Unit)? = null,
    topBarNavImageVector: ImageVector? = Icons.AutoMirrored.Outlined.ArrowBack,
    @DrawableRes topBarNavPainterResId: Int? = null,
    @StringRes topBarNavCntDescResId: Int? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    onTopBarNavClick: (() -> Unit)? = null,
    defTopBarActions: @Composable RowScope.() -> Unit = {},
    topBarActions: @Composable RowScope.() -> Unit = {},
    barActionItems: List<BarActionItem> = emptyList(),
    isActionsLeading: Boolean = true,
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    userRoles: List<MemberRoleType> = emptyList(),
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
        }*/
    val modifier = Modifier.fillMaxSize()
    val title = topBarTitle ?: //LocalAppState.current.appName.plus(
    topBarTitleResId?.let { stringResource(it) } ?: navRoute?.titleResId?.let { stringResource(it) }
    ?: appState.actionBarTitle.value
    val subTitle = topBarSubtitle ?: appState.actionBarSubtitle.value.ifEmpty { null }
    Scaffold(
        modifier = nestedScrollConnection?.let {
            if (isNestedScrollConnection) modifier.nestedScroll(it) else null
        } ?: modifier,
        topBar = {
            topBar?.invoke() ?: TopAppBar(
                title = {
                    actionBar?.invoke() ?: Column(verticalArrangement = Arrangement.Center) {
                        Row {
                            navRoute?.let {
                                IconComponent(
                                    imageVector = it.iconImageVector,
                                    painterResId = it.iconPainterResId,
                                    //contentDescriptionResId = it.
                                )
                            }
                            Text(
                                modifier = Modifier.padding(start = 8.dp),
                                text = title,
                                fontWeight = FontWeight.Bold,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        subTitle?.let {
                            Text(
                                text = it,
                                style = Typography.titleMedium,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                },
                navigationIcon = navigationIcon?.let { { it() } } ?: {
                    // check icons for auth screens: Signup and Login
                    if (topBarNavImageVector != null || topBarNavPainterResId != null) {
                        topBarNavImageVector?.let {
                            IconButton(
                                onClick = onTopBarNavClick ?: appState.handleTopBarNavClick.value
                            ) {
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
                    if (barActionItems.isNotEmpty()) {
                        if (isActionsLeading.not()) {
                            defTopBarActions()
                        }
                        repeat(barActionItems.size) {
                            if (userRoles.isEmpty() || barActionItems[it].userRoles.isEmpty() ||
                                barActionItems[it].userRoles.any { role ->
                                    MemberRoleType.valueOf(role) in userRoles
                                }
                            ) {
                                IconButton(onClick = barActionItems[it].onClick) {
                                    IconComponent(
                                        imageVector = barActionItems[it].iconImageVector,
                                        painterResId = barActionItems[it].iconPainterResId,
                                        contentDescriptionResId = barActionItems[it].cntDescResId
                                    )
                                }
                            }
                        }
                        if (isActionsLeading) {
                            defTopBarActions()
                        }
                    } else {
                        if (isActionsLeading) {
                            topBarActions(); defTopBarActions()
                        } else {
                            defTopBarActions(); topBarActions()
                        }
                    }
                }
            )
        },
        floatingActionButtonPosition = floatingActionButtonPosition,
        floatingActionButton = floatingActionButton,
        bottomBar = bottomBar
    ) {
        content(it)
    }
}