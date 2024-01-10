package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.buttons.SaveButtonComponent
import com.oborodulin.home.common.ui.components.screen.DialogScreenComponent
import com.oborodulin.home.common.util.toUUIDOrNull
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryInput
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_territory.R
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Territoring.TerritoryScreen"

@Composable
fun TerritoryScreen(
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    viewModel: TerritoryViewModelImpl = hiltViewModel(),
    territoryInput: TerritoryInput? = null,
    defTopBarActions: @Composable RowScope.() -> Unit = {}/*,
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit*/
) {
    Timber.tag(TAG).d("TerritoryScreen(...) called: territoryInput = %s", territoryInput)
    val appState = LocalAppState.current
    val upNavigation = { appState.mainNavigateUp() } //backToBottomBarScreen() }
    val territoryId = viewModel.id.collectAsStateWithLifecycle().value.value.toUUIDOrNull()
    Timber.tag(TAG).d("TerritoryScreen: territoryId (id) = %s", territoryId)
    /*
    LaunchedEffect(Unit) {
        if (LOG_FLOW_ACTION) Timber.tag(TAG)
            .d(
                "TerritoryScreen -> LaunchedEffect(Unit): territoryId = %s; territoryInput.territoryId = %s",
                territoryId, territoryInput?.territoryId
            )
        territoryId?.let {
            if (territoryInput?.territoryId == null) viewModel.submitAction(TerritoryUiAction.Load())
        }
    }*/
    var topBarActions: @Composable RowScope.() -> Unit by remember { mutableStateOf(@Composable {}) }
    val onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit = { topBarActions = it }
    var actionBarSubtitle by rememberSaveable { mutableStateOf("") }
    val onActionBarSubtitleChange: (String) -> Unit = { actionBarSubtitle = it }
    ScaffoldComponent(
        topBarSubtitle = actionBarSubtitle,
        defTopBarActions = defTopBarActions,
        topBarActions = topBarActions
    ) { innerPadding ->
        DialogScreenComponent(
            viewModel = viewModel,
            inputId = territoryInput?.territoryId,
            loadUiAction = TerritoryUiAction.Load(territoryInput?.territoryId),
            saveUiAction = TerritoryUiAction.Save,
            nextAction = {
                viewModel.submitAction(TerritoryUiAction.EditTerritoryDetails(viewModel.id()))
            },
            upNavigation = upNavigation,
            handleTopBarNavClick = appState.handleTopBarNavClick,
            topBarActionImageVector = Icons.Outlined.ArrowForward,
            topBarActionCntDescResId = com.oborodulin.home.common.R.string.btn_next_cnt_desc,
            cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_territory,
            uniqueConstraintFailedResId = R.string.territory_unique_constraint_error,
            confirmButton = { areValid, handleSaveButtonClick ->
                SaveButtonComponent(enabled = areValid, onClick = handleSaveButtonClick)
            },
            /*onActionBarChange = onActionBarChange,
    onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,*/
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarActionsChange = onTopBarActionsChange,
            //onFabChange = onFabChange
            innerPadding = innerPadding
        ) { _, _, _, handleSaveAction ->
            TerritoryView(
                sharedViewModel = appState.congregationSharedViewModel.value,
                viewModel = viewModel,
                handleSaveAction = handleSaveAction
            )
        }
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