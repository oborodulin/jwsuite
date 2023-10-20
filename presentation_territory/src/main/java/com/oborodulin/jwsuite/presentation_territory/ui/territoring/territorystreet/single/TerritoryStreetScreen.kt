package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.single

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.buttons.SaveButtonComponent
import com.oborodulin.home.common.ui.components.dialog.alert.CancelChangesConfirmDialogComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryStreetInput
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModel
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.list.TerritoryStreetsListUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorystreet.list.TerritoryStreetsListViewModelImpl
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Territoring.TerritoryStreetScreen"

@Composable
fun TerritoryStreetScreen(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    territoryViewModel: TerritoryViewModel,
    territoryStreetsListViewModel: TerritoryStreetsListViewModelImpl = hiltViewModel(),
    territoryStreetViewModel: TerritoryStreetViewModelImpl = hiltViewModel(),
    territoryStreetInput: TerritoryStreetInput? = null,
    paddingValues: PaddingValues,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavClickChange: (() -> Unit) -> Unit,
    onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit
) {
    Timber.tag(TAG)
        .d("TerritoryStreetScreen(...) called: territoryStreetInput = %s", territoryStreetInput)
    val coroutineScope = rememberCoroutineScope()
    val handleSaveButtonClick = {
        Timber.tag(TAG).d("TerritoryStreetScreen(...): Save Button onClick...")
        // checks all errors
        territoryStreetViewModel.onContinueClick {
            // if success, backToBottomBarScreen
            // https://stackoverflow.com/questions/72987545/how-to-navigate-to-another-screen-after-call-a-viemodelscope-method-in-viewmodel
            coroutineScope.launch {
                territoryStreetViewModel.actionsJobFlow.collectLatest { job ->
                    Timber.tag(TAG).d(
                        "TerritoryStreetScreen(...): Start actionsJobFlow.collect [job = %s]",
                        job?.toString()
                    )
                    job?.join()
                    appState.backToBottomBarScreen()
                }
            }
            // save
            territoryStreetViewModel.submitAction(TerritoryStreetUiAction.Save)
        }
    }
    LaunchedEffect(territoryStreetInput?.territoryStreetId) {
        Timber.tag(TAG).d("TerritoryStreetScreen: LaunchedEffect() BEFORE collect ui state flow")
        territoryStreetViewModel.submitAction(
            TerritoryStreetUiAction.Load(
                territoryStreetInput?.territoryId, territoryStreetInput?.territoryStreetId
            )
        )
        territoryStreetInput?.territoryId?.let {
            territoryStreetsListViewModel.submitAction(TerritoryStreetsListUiAction.Load(it))
        }
    }
    territoryStreetViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        territoryStreetViewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let {
            onActionBarSubtitleChange(stringResource(it))
        }
        val backNavigation: () -> Unit = { appState.commonNavigateUp() }
        // Cancel Changes Confirm:
        val isUiStateChanged by territoryStreetViewModel.isUiStateChanged.collectAsStateWithLifecycle()
        val isCancelChangesShowAlert = rememberSaveable { mutableStateOf(false) }
        CancelChangesConfirmDialogComponent(
            isShow = isCancelChangesShowAlert,
            text = stringResource(R.string.dlg_confirm_cancel_changes_territory_category),
            onConfirm = backNavigation
        )
        // Scaffold Hoisting:
        onTopBarNavClickChange {
            if (isUiStateChanged) isCancelChangesShowAlert.value = true else backNavigation()
        }
        val areInputsValid by territoryStreetViewModel.areInputsValid.collectAsStateWithLifecycle()
        onTopBarActionsChange {
            IconButton(enabled = areInputsValid, onClick = handleSaveButtonClick) {
                Icon(Icons.Outlined.Done, null)
            }
        }
        CommonScreen(paddingValues = paddingValues, state = state) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TerritoryStreetView(
                    uiModel = it, sharedViewModel = appState.sharedViewModel.value,
                    territoryViewModel = territoryViewModel
                )
                Spacer(Modifier.height(8.dp))
                SaveButtonComponent(enabled = areInputsValid, onClick = handleSaveButtonClick)
            }
        }
    }
}