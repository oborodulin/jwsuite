package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.screen.SaveDialogScreenComponent
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryHouseInput
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModel
import timber.log.Timber

private const val TAG = "Territoring.TerritoryHouseScreen"

@Composable
fun TerritoryHouseScreen(
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    territoryViewModel: TerritoryViewModel,
    territoryHouseViewModel: TerritoryHouseViewModelImpl = hiltViewModel(),
    territoryHouseInput: TerritoryHouseInput,
    defTopBarActions: @Composable RowScope.() -> Unit = {}/*,
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit*/
) {
    Timber.tag(TAG)
        .d("TerritoryHouseScreen(...) called: territoryHouseInput = %s", territoryHouseInput)
    val appState = LocalAppState.current
    val upNavigation: () -> Unit = { appState.mainNavigateUp() }
    var topBarActions: @Composable RowScope.() -> Unit by remember { mutableStateOf(@Composable {}) }
    val onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit = { topBarActions = it }
    territoryHouseViewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let {
        ScaffoldComponent(
            topBarTitleResId = com.oborodulin.jwsuite.presentation.R.string.nav_item_territoring,
            topBarSubtitle = stringResource(it),
            defTopBarActions = defTopBarActions,
            topBarActions = topBarActions
        ) { innerPadding ->
            SaveDialogScreenComponent(
                viewModel = territoryHouseViewModel,
                inputId = territoryHouseInput.territoryId,
                loadUiAction = TerritoryHouseUiAction.Load(territoryHouseInput.territoryId),
                saveUiAction = TerritoryHouseUiAction.Save,
                upNavigation = upNavigation,
                handleTopBarNavClick = appState.handleTopBarNavClick,
                cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_territory_house,
                /*onActionBarChange = onActionBarChange,
        onActionBarSubtitleChange = onActionBarSubtitleChange,
        onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,*/
                onTopBarActionsChange = onTopBarActionsChange,
                //onFabChange = onFabChange
                innerPadding = innerPadding
            ) { uiModel ->
                TerritoryHouseView(
                    territoryHousesUiModel = uiModel,
                    sharedViewModel = appState.congregationSharedViewModel.value,
                    territoryViewModel = territoryViewModel,
                    territoryHouseViewModel = territoryHouseViewModel
                )
            }
        }
    }
}