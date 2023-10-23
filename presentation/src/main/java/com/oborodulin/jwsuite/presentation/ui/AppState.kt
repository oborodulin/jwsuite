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
    //accountingScaffoldState: ScaffoldState = rememberScaffoldState(),
    //payerScaffoldState: ScaffoldState = rememberScaffoldState(),
    commonNavController: NavHostController = rememberNavController(),
    navBarNavController: NavHostController = rememberNavController(),
    sharedViewModel: MutableState<SharedViewModeled<ListItemModel?>?> = remember {
        mutableStateOf(null)
    },

//    fab: MutableState<@Composable () -> Unit> = remember { mutableStateOf({ }) },
    resources: Resources = LocalContext.current.resources,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    appName: String = "",
    actionBarSubtitle: MutableState<String> = rememberSaveable { mutableStateOf("") }
) = remember(
        //accountingScaffoldState,
        //payerScaffoldState,
        commonNavController,
        //navBarNavController,
        sharedViewModel,
//        fab,
        resources,
        coroutineScope,
        actionBarSubtitle
    ) {
        AppState(
            //accountingScaffoldState,
            //payerScaffoldState,
            commonNavController,
            commonNavController, //navBarNavController,
            sharedViewModel,
//            fab,
            appName,
            actionBarSubtitle
        )
    }

/**
 * Responsible for holding state related to [App] and containing UI-related logic.
 */
// https://foso.github.io/Jetpack-Compose-Playground/general/compositionlocal/
val LocalAppState = compositionLocalOf<AppState> { error("No App state found!") }

@Stable
class AppState(
    val commonNavController: NavHostController,
    val navBarNavController: NavHostController,
    val sharedViewModel: MutableState<SharedViewModeled<ListItemModel?>?>,
//    val fab: MutableState<@Composable () -> Unit>,
    val appName: String,
    val actionBarSubtitle: MutableState<String>
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
        @Composable get() = commonNavController
            .currentBackStackEntryAsState().value?.destination?.route in bottomNavBarRoutes

    // ----------------------------------------------------------
    // Источник состояния навигации
    // ----------------------------------------------------------

    val navBarCurrentRoute: String?
        get() = this.navBarNavController.currentDestination?.route

    fun navBarUpPress() = this.navBarNavController.navigateUp()

    fun commonNavigateUp() = this.commonNavController.navigateUp()

    // Возврат к экрану из главного меню нижней панели.
    fun backToBottomBarScreen() {
        Timber.tag(TAG).d("backToBottomBarScreen() called")
        this.commonNavController.popBackStack()
        this.commonNavController.navigate(NavRoutes.Home.route) {
            popUpTo(commonNavController.graph.startDestinationId)
            launchSingleTop = true
        }
    }

    // Переход по маршруту с пропуском предыдущего экрана.
    // https://stackoverflow.com/questions/66845899/compose-navigation-remove-previous-composable-from-stack-before-navigating
    // https://stackoverflow.com/questions/75978612/skip-back-stack-items-on-jetpack-compose-navigation
    fun navigateToRoute(route: String, skippedPrevRoute: String? = null) {
        Timber.tag(TAG)
            .d(
                "navigateToRoute(...) called: route = %s; skippedPrevRoute = %s",
                route,
                skippedPrevRoute
            )
        this.commonNavController.navigate(route) {
            skippedPrevRoute?.let {
                popUpTo(it) { inclusive = true }
            }
        }
    }

    // Клик по навигационному меню, вкладке.
    fun navigateToBottomBarRoute(route: String) {
        if (route != this.navBarCurrentRoute) {
            this.navBarNavController.navigate(route) {
                // Pop up to the start destination of the graph to
                // avoid building up a large stack of destinations
                // on the back stack AS users select items
                //Возвращаем выбранный экран,
                //иначе если backstack не пустой то показываем ранее открытое состяние
                navBarNavController.graph.startDestinationRoute?.let { route ->
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