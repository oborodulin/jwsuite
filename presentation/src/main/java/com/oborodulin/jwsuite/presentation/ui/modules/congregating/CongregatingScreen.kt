package com.oborodulin.jwsuite.presentation.ui.modules.congregating

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.oborodulin.jwsuite.presentation.ui.congregating.congregation.list.CongregationsListView
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.ui.theme.HomeComposableTheme
import com.oborodulin.home.common.util.toast
import com.oborodulin.jwsuite.presentation.AppState
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.inputs.CongregationInput
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.util.*

/**
 * Created by o.borodulin 10.June.2023
 */
private const val TAG = "Congregating.ui.CongregatingScreen"

@Composable
fun CongregatingScreen(
    viewModel: CongregatingViewModelImpl = hiltViewModel(),
    appState: AppState,
    nestedScrollConnection: NestedScrollConnection,
    bottomBar: @Composable () -> Unit
) { //setFabOnClick: (() -> Unit) -> Unit
    Timber.tag(TAG).d("CongregatingScreen(...) called")
    val context = LocalContext.current
    var showBackButton = false
    /*{
        FabComponent(text = "TODO", onClick = {
            Toast.makeText(context, "Added Todo", Toast.LENGTH_SHORT).show()
        }
        )
    }*/
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("CongregatingScreen: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(CongregatingUiAction.Init)
    }
/*    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            // You can map the title based on the route using:
            backStackEntry.destination.route?.let {
                currentRoute = it
                actionBarTitle = getTitleByRoute(context, it)
            }
        }
    }
     = when (currentRoute) {
        NavRoutes.Payer.route -> true
        else -> false
    }
    */
    viewModel.uiStateFlow.collectAsState().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        HomeComposableTheme { //(darkTheme = true)
            ScaffoldComponent(
                appState = appState,
                //scaffoldState = appState.accountingScaffoldState,
                nestedScrollConnection = nestedScrollConnection,
                topBarTitleId = com.oborodulin.jwsuite.presentation.R.string.nav_item_congregating,
                topBarActions = {
                    IconButton(onClick = { appState.commonNavController.navigate(NavRoutes.Congregation.routeForCongregation()) }) {
                        Icon(Icons.Filled.Add, null)
                    }
                    IconButton(onClick = { context.toast("Settings button clicked...") }) {
                        Icon(Icons.Filled.Settings, null)
                    }
                },
                bottomBar = bottomBar
            ) {
                CommonScreen(paddingValues = it, state = state) { accountingUi ->
                    var payerId = UUID.randomUUID()
                    accountingUi.favoritePayer?.let { payer ->
                        appState.actionBarSubtitle.value = payer.territoryMark
                        viewModel.setPrimaryObjectData(
                            arrayListOf(
                                payer.id.toString(),
                                payer.congregationName
                            )
                        )
                        payer.id?.let { id -> payerId = id }
                    }
                    CongregationsListView(
                        appState = appState,
                        navController = appState.commonNavController,
                        congregationInput = CongregationInput(payerId)
                    )
                }
            }
        }
        LaunchedEffect(Unit) {
            Timber.tag(TAG).d("CongregatingScreen: LaunchedEffect() AFTER collect ui state flow")
            viewModel.singleEventFlow.collectLatest {
                Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
                when (it) {
                    is CongregatingUiSingleEvent.OpenPayerScreen -> {
                        appState.commonNavController.navigate(it.navRoute)
                    }
                }
            }
        }
    }
}