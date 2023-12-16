package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.single

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.screen.SaveDialogScreenComponent
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryStreetInput
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModel
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.list.TerritoryStreetsListUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.list.TerritoryStreetsListViewModelImpl
import timber.log.Timber

private const val TAG = "Territoring.TerritoryStreetScreen"

@Composable
fun TerritoryStreetScreen(
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    territoryViewModel: TerritoryViewModel,
    territoryStreetsListViewModel: TerritoryStreetsListViewModelImpl = hiltViewModel(),
    territoryStreetViewModel: TerritoryStreetViewModelImpl = hiltViewModel(),
    territoryStreetInput: TerritoryStreetInput? = null,
    defTopBarActions: @Composable RowScope.() -> Unit = {}/*,
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit*/
) {
    Timber.tag(TAG)
        .d("TerritoryStreetScreen(...) called: territoryStreetInput = %s", territoryStreetInput)
    val appState = LocalAppState.current
    val upNavigation = { appState.mainNavigateUp() }

    LaunchedEffect(territoryStreetInput?.territoryId) {
        Timber.tag(TAG).d("TerritoryStreetScreen -> LaunchedEffect() BEFORE collect ui state flow")
        territoryStreetInput?.territoryId?.let {
            territoryStreetsListViewModel.submitAction(TerritoryStreetsListUiAction.Load(it))
        }
    }
    var topBarActions: @Composable RowScope.() -> Unit by remember { mutableStateOf(@Composable {}) }
    val onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit = { topBarActions = it }
    territoryStreetViewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let {
        ScaffoldComponent(
            topBarTitleResId = com.oborodulin.jwsuite.presentation.R.string.nav_item_territoring,
            topBarSubtitle = stringResource(it),
            defTopBarActions = defTopBarActions,
            topBarActions = topBarActions
        ) { innerPadding ->
            SaveDialogScreenComponent(
                viewModel = territoryStreetViewModel,
                inputId = territoryStreetInput?.territoryStreetId,
                loadUiAction = TerritoryStreetUiAction.Load(
                    territoryStreetInput?.territoryId, territoryStreetInput?.territoryStreetId
                ),
                saveUiAction = TerritoryStreetUiAction.Save,
                upNavigation = upNavigation,
                handleTopBarNavClick = appState.handleTopBarNavClick,
                cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_territory_street,
                /*onActionBarChange = onActionBarChange,
        onActionBarSubtitleChange = onActionBarSubtitleChange,
        onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,*/
                onTopBarActionsChange = onTopBarActionsChange,
                //onFabChange = onFabChange
                innerPadding = innerPadding
            ) { uiModel ->
                TerritoryStreetView(
                    uiModel = uiModel, sharedViewModel = appState.congregationSharedViewModel.value,
                    territoryViewModel = territoryViewModel
                )
            }
        }
    }
}