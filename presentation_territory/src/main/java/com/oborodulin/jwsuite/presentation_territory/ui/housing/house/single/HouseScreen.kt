package com.oborodulin.jwsuite.presentation_territory.ui.housing.house.single

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.oborodulin.home.common.ui.components.screen.SaveDialogScreenComponent
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.HouseInput
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_territory.R
import timber.log.Timber

private const val TAG = "Territoring.HouseScreen"

@Composable
fun HouseScreen(
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    //territoryViewModel: TerritoryViewModelImpl = hiltViewModel(),
    viewModel: HouseViewModelImpl = hiltViewModel(),
    houseInput: HouseInput? = null,
    defTopBarActions: @Composable RowScope.() -> Unit = {}/*,
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit*/
) {
    Timber.tag(TAG).d("HouseScreen(...) called: houseInput = %s", houseInput)
    val appState = LocalAppState.current
    val upNavigation: () -> Unit = { appState.mainNavigateUp(NavRoutes.Housing.route) }
    var topBarActions: @Composable RowScope.() -> Unit by remember { mutableStateOf(@Composable {}) }
    val onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit = { topBarActions = it }
    var actionBarSubtitle by rememberSaveable { mutableStateOf("") }
    val onActionBarSubtitleChange: (String) -> Unit = { actionBarSubtitle = it }
    ScaffoldComponent(
        topBarTitleResId = com.oborodulin.jwsuite.presentation.R.string.nav_item_housing,
        topBarSubtitle = actionBarSubtitle,
        defTopBarActions = defTopBarActions,
        topBarActions = topBarActions
    ) { innerPadding ->
        SaveDialogScreenComponent(
            viewModel = viewModel,
            inputId = houseInput?.houseId,
            loadUiAction = HouseUiAction.Load(houseInput?.houseId),
            saveUiAction = HouseUiAction.Save,
            upNavigation = upNavigation,
            handleTopBarNavClick = appState.handleTopBarNavClick,
            cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_house,
            uniqueConstraintFailedResId = R.string.house_unique_constraint_error,
            /*onActionBarChange = onActionBarChange,
    onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,*/
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarActionsChange = onTopBarActionsChange,
            //onFabChange = onFabChange
            innerPadding = innerPadding
        ) { HouseView(sharedViewModel = appState.congregationSharedViewModel.value) }
    }
}