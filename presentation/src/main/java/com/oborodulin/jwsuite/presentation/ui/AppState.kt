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
    actionBarSubtitle: MutableState<String> = rememberSaveable { mutableStateOf("") },
    handleTopBarNavClick: MutableState<() -> Unit> = remember { mutableStateOf({}) }
) = remember(
    mainNavController,//, mainNavController, mainNavController,// rootNavController, barNavController,
    congregationSharedViewModel,
    memberSharedViewModel,
    resources,
    coroutineScope,
    actionBarSubtitle, handleTopBarNavClick
) {
    AppState(
        rootNavController = mainNavController, // rootNavController,
        mainNavController = mainNavController,
        barNavController = mainNavController, //barNavController,
        congregationSharedViewModel = congregationSharedViewModel,
        memberSharedViewModel = memberSharedViewModel,
        appName = appName,
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
    val appName: String,
    val actionBarSubtitle: MutableState<String>,
    val handleTopBarNavClick: MutableState<() -> Unit>
) {
    // ----------------------------------------------------------
    // Источник состояния TopAppBar
    // ----------------------------------------------------------

    // ----------------------------------------------------------
    // Источник состояния BottomBar
    // ----------------------------------------------------------

    private val bottomNavBarTabs = NavRoutes.bottomNavBarRoutes()
    private val bottomNavBarRoutes = bottomNavBarTabs.map { it.route }

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
        Timber.tag(TAG).d("mainNavigateUp(...) called: destination = %s", destination)
        if (!this.mainNavController.navigateUp()) {
            destination?.let {
                this.mainNavController.navigate(it) {
                    Timber.tag(TAG)
                        .d("mainNavigateUp -> mainNavController.navigate(%s)", destination)
                    popUpTo(destination) { inclusive = true }
                }
            }
        }
    }

    // Возврат к экрану из главного меню нижней панели.
    fun backToBottomBarScreen() {
        Timber.tag(TAG).d("backToBottomBarScreen() called")
        this.mainNavController.popBackStack()
        this.mainNavController.navigate(NavRoutes.Home.route) {
            popUpTo(mainNavController.graph.startDestinationId)
            launchSingleTop = true
        }
    }

    fun mainNavigate(route: String) {
        Timber.tag(TAG).d("mainNavigate(...) called: route = %s", route)
        this.mainNavController.navigate(route)
    }

    fun navigateByDestination(destination: String) {
        Timber.tag(TAG).d("navigateByRoute(...) called: destination = %s", destination)
        when {
            NavRoutes.rootRoutes().map { it.route }
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
        Timber.tag(TAG)
            .d(
                "mainNavigateToRoute(...) called: route = %s; skippedPrevRoute = %s",
                route, skippedPrevRoute
            )
        this.mainNavController.navigate(route) {
            skippedPrevRoute?.let {
                popUpTo(it) { inclusive = true }
            }
        }
    }

    // Клик по навигационному меню, вкладке.
    fun navigateToBarRoute(route: String) {
        Timber.tag(TAG).d("navigateToBarRoute(...) called: route = %s", route)
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