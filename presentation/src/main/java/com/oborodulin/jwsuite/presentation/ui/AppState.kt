package com.oborodulin.jwsuite.presentation.ui

import android.content.res.Resources
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SharedViewModeled
import com.oborodulin.home.common.util.LogLevel.LOG_NAVIGATION
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber

/**
 * Remembers and creates an instance of [AppState]
 */
private const val TAG = "Presentation.AppState"

@Composable
fun rememberAppState(
    //rootNavController: NavHostController = rememberNavController(),
    mainNavController: NavHostController = rememberNavController(),
    //barNavController: NavHostController = rememberNavController(),
    congregationSharedViewModel: MutableState<SharedViewModeled<ListItemModel?>?> = remember {
        mutableStateOf(null)
    },
    memberSharedViewModel: MutableState<SharedViewModeled<ListItemModel?>?> = remember {
        mutableStateOf(null)
    },
    resources: Resources = LocalContext.current.resources,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    appName: String = "",
    actionBarTitle: MutableState<String> = rememberSaveable { mutableStateOf("") },
    actionBarSubtitle: MutableState<String> = rememberSaveable { mutableStateOf("") },
    handleTopBarNavClick: MutableState<() -> Unit> = remember { mutableStateOf({}) }
) = remember(
    mainNavController,//, mainNavController, mainNavController,// rootNavController, barNavController,
    congregationSharedViewModel,
    memberSharedViewModel,
    resources,
    coroutineScope,
    actionBarTitle, actionBarSubtitle, handleTopBarNavClick
) {
    AppState(
        rootNavController = mainNavController, // rootNavController,
        mainNavController = mainNavController,
        barNavController = mainNavController, //barNavController,
        congregationSharedViewModel = congregationSharedViewModel,
        memberSharedViewModel = memberSharedViewModel,
        resources = resources,
        appName = appName,
        actionBarTitle = actionBarTitle,
        actionBarSubtitle = actionBarSubtitle,
        handleTopBarNavClick = handleTopBarNavClick
    )
}

/**
 * Responsible for holding state related to [App] and containing UI-related logic.
 */
// https://foso.github.io/Jetpack-Compose-Playground/general/compositionlocal/
val LocalAppState = compositionLocalOf<AppState> { error("No App state found!") }

@Stable
class AppState(
    val rootNavController: NavHostController,
    val mainNavController: NavHostController,
    val barNavController: NavHostController,
    val congregationSharedViewModel: MutableState<SharedViewModeled<ListItemModel?>?>,
    val memberSharedViewModel: MutableState<SharedViewModeled<ListItemModel?>?>,
    val resources: Resources,
    val appName: String,
    val actionBarTitle: MutableState<String>,
    val actionBarSubtitle: MutableState<String>,
    val handleTopBarNavClick: MutableState<() -> Unit>
) {
    // ----------------------------------------------------------
    // Источник состояния TopAppBar
    // ----------------------------------------------------------

    private fun setActionBarTitle(navRoutes: List<NavRoutes> = emptyList(), route: String) {
        navRoutes.find { it.route == route }?.let {
            this.actionBarTitle.value = resources.getString(it.titleResId)
        }
    }

    // ----------------------------------------------------------
    // Источник состояния BottomBar
    // ----------------------------------------------------------

    private val bottomNavBarTabs = NavRoutes.bottomNavBarRoutes()
    private val bottomNavBarRoutes = bottomNavBarTabs.map { it.route }

    private val allAggregationNavTabs =
        NavRoutes.aggregationRoutes().toMutableList().union(NavRoutes.bottomNavBarRoutes())

    // Атрибут отображения навигационного меню bottomBar
    // https://stackoverflow.com/questions/76835709/right-strategy-of-using-bottom-navigation-bar-with-jetpack-compose
    val shouldShowBottomNavBar: Boolean
        @Composable get() = barNavController.currentBackStackEntryAsState().value?.destination?.route in bottomNavBarRoutes //&&
    //mainNavController.currentBackStackEntryAsState().value?.destination?.route == NavRoutes.Home.route

    // ----------------------------------------------------------
    // Источник состояния навигации
    // ----------------------------------------------------------

    val barNavCurrentRoute: String?
        get() = this.barNavController.currentDestination?.route

    val mainNavCurrentRoute: String?
        get() = this.mainNavController.currentDestination?.route

    fun mainNavigateUp(destination: String? = null) {
        if (LOG_NAVIGATION) {
            Timber.tag(TAG).d("mainNavigateUp(...) called: destination = %s", destination)
        }
        this.mainNavController.navigateUp()
        destination?.let { route ->
            this.setActionBarTitle(allAggregationNavTabs.toList(), route)
            this.mainNavController.navigate(route) {
                launchSingleTop = true
                popUpTo(route)
            }
        }
        /*if (!this.mainNavController.navigateUp()) {
            destination?.let {
                this.mainNavController.navigate(it) {
                    Timber.tag(TAG)
                        .d("mainNavigateUp -> mainNavController.navigate(%s)", destination)
                    popUpTo(destination) { inclusive = true }
                }
            }
        }*/
    }

    // Возврат к экрану из главного меню нижней панели.
    fun backToBottomBarScreen(destination: String? = null) {
        val dbgMsg = "backToBottomBarScreen(...) called: destination = %s".format(destination)
        destination?.let {
            if (!bottomNavBarRoutes.contains(it)) throw IllegalArgumentException(dbgMsg)
        }
        if (LOG_NAVIGATION) {
            Timber.tag(TAG).d(dbgMsg)
        }
        this.mainNavController.popBackStack()
        destination?.let { route ->
            this.setActionBarTitle(bottomNavBarTabs, route)
            this.mainNavController.navigate(route) {// NavRoutes.Home.route
                popUpTo(mainNavController.graph.startDestinationId)
                launchSingleTop = true
            }
        }
    }

    fun mainNavigate(route: String) {
        if (LOG_NAVIGATION) {
            Timber.tag(TAG).d("mainNavigate(...) called: route = %s", route)
        }
        this.mainNavController.navigate(route)
        this.setActionBarTitle(allAggregationNavTabs.toList(), route)
    }

    fun navigateByDestination(destination: String) {
        if (LOG_NAVIGATION) {
            Timber.tag(TAG).d("navigateByRoute(...) called: destination = %s", destination)
        }
        when {
            NavRoutes.authRoutes().map { it.route }
                .contains(destination) -> this.rootNavController.navigate(destination) {
                popUpTo(destination) {
                    inclusive = true
                }
            }

            NavRoutes.bottomNavBarRoutes().map { it.route }
                .contains(destination) -> this.barNavController.navigate(destination) {
                popUpTo(destination) {
                    inclusive = true
                }
            }

            else -> this.mainNavController.navigate(destination)
        }
    }

    // Переход по маршруту с пропуском предыдущего экрана.
    // https://stackoverflow.com/questions/66845899/compose-navigation-remove-previous-composable-from-stack-before-navigating
    // https://stackoverflow.com/questions/75978612/skip-back-stack-items-on-jetpack-compose-navigation
    fun mainNavigateToRoute(route: String, skippedPrevRoute: String? = null) {
        if (LOG_NAVIGATION) {
            Timber.tag(TAG)
                .d(
                    "mainNavigateToRoute(...) called: route = %s; skippedPrevRoute = %s",
                    route, skippedPrevRoute
                )
        }
        this.mainNavController.navigate(route) {
            skippedPrevRoute?.let {
                popUpTo(it) { inclusive = true }
            }
        }
    }

    // Клик по навигационному меню, вкладке.
    fun navigateToBarRoute(route: String) {
        val dbgMsg = "navigateToBarRoute(...) called: route = %s".format(route)
        if (!bottomNavBarRoutes.contains(route)) throw IllegalArgumentException(dbgMsg)
        if (LOG_NAVIGATION) {
            Timber.tag(TAG).d(dbgMsg)
        }
        this.setActionBarTitle(bottomNavBarTabs, route)
        if (route != this.barNavCurrentRoute) {
            this.barNavController.navigate(route) {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack AS users select items
                //Возвращаем выбранный экран,
                //иначе если backstack не пустой то показываем ранее открытое состяние
                barNavController.graph.startDestinationRoute?.let { route ->
                    popUpTo(route) {
                        saveState = true
                    }
                }

                /**
                 * As per https://developer.android.com/jetpack/compose/navigation#bottom-nav
                 * By using the saveState and restoreState flags,
                 * the state and back stack of that item is correctly saved
                 * and restored AS you swap between bottom navigation items.
                 */
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true
                // Restore state when reselecting a previously selected item
                restoreState = true
            }
        }
    }
}