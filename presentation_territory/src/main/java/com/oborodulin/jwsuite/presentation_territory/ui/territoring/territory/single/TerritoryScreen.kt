package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.vector.ImageVector
import com.oborodulin.home.common.ui.components.screen.SaveDialogScreenComponent
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_territory.R
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Territoring.TerritoryScreen"

@Composable
fun TerritoryScreen(
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    viewModel: TerritoryViewModel,
    territoryInput: TerritoryInput? = null,
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit
) {
    Timber.tag(TAG).d("TerritoryScreen(...) called: territoryInput = %s", territoryInput)
    val appState = LocalAppState.current
    val upNavigation = { appState.mainNavigateUp() } //backToBottomBarScreen() }
    //val territoryId by viewModel.id.collectAsStateWithLifecycle()
    //Timber.tag(TAG).d("TerritoryScreen: territoryId = %s", territoryId)
    SaveDialogScreenComponent(
        viewModel = viewModel,
        inputId = territoryInput?.territoryId,
        loadUiAction = TerritoryUiAction.Load(territoryInput?.territoryId),
        saveUiAction = TerritoryUiAction.Save,
        nextAction = { viewModel.submitAction(TerritoryUiAction.EditTerritoryDetails(viewModel.id())) },
        upNavigation = upNavigation,
        handleTopBarNavClick = appState.handleTopBarNavClick,
        topBarActionImageVector = Icons.Outlined.ArrowForward,
        topBarActionCntDescResId = com.oborodulin.home.common.R.string.btn_next_cnt_desc,
        cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_territory,
        uniqueConstraintFailedResId = R.string.territory_unique_constraint_error,
        onActionBarChange = onActionBarChange,
        onActionBarSubtitleChange = onActionBarSubtitleChange,
        onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
        onTopBarActionsChange = onTopBarActionsChange,
        onFabChange = onFabChange
    ) {
        TerritoryView(appState.congregationSharedViewModel.value, viewModel = viewModel)
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("TerritoryScreen -> LaunchedEffect() AFTER collect single Event Flow")
        viewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is TerritoryUiSingleEvent.OpenTerritoryDetailsScreen -> {
                    appState.mainNavigate(it.navRoute)
                }
            }
        }
    }
}