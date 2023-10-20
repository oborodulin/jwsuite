package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.territory

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
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryHouseInput
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModel
import timber.log.Timber

private const val TAG = "Territoring.TerritoryHouseScreen"

@Composable
fun TerritoryHouseScreen(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    territoryViewModel: TerritoryViewModel,
    territoryHouseViewModel: TerritoryHouseViewModelImpl = hiltViewModel(),
    territoryHouseInput: TerritoryHouseInput? = null,
    paddingValues: PaddingValues,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavClickChange: (() -> Unit) -> Unit,
    onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit
) {
    Timber.tag(TAG)
        .d("TerritoryHouseScreen(...) called: territoryHouseInput = %s", territoryHouseInput)

    val handleSaveButtonClick = {
        // checks all errors
        territoryHouseViewModel.onContinueClick {
            Timber.tag(TAG).d("TerritoryHouseScreen(...): Save Button onClick...")
            // if success, save then backToBottomBarScreen
            territoryHouseViewModel.handleActionJob(
                { territoryHouseViewModel.submitAction(TerritoryHouseUiAction.Save) },
                { appState.commonNavigateUp() })
        }
    }
    LaunchedEffect(territoryHouseInput?.territoryId) {
        Timber.tag(TAG).d("TerritoryHouseScreen: LaunchedEffect() BEFORE collect ui state flow")
        territoryHouseInput?.let {
            territoryHouseViewModel.submitAction(TerritoryHouseUiAction.Load(it.territoryId))
        }
    }
    territoryHouseViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        territoryHouseViewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let {
            onActionBarSubtitleChange(stringResource(it))
        }
        val backNavigation: () -> Unit = { appState.commonNavigateUp() }
        // Cancel Changes Confirm:
        val isUiStateChanged by territoryHouseViewModel.isUiStateChanged.collectAsStateWithLifecycle()
        val isCancelChangesShowAlert = rememberSaveable { mutableStateOf(false) }
        CancelChangesConfirmDialogComponent(
            isShow = isCancelChangesShowAlert,
            text = stringResource(R.string.dlg_confirm_cancel_changes_territory_house),
            onConfirm = backNavigation
        )
        // Scaffold Hoisting:
        onTopBarNavClickChange {
            if (isUiStateChanged) isCancelChangesShowAlert.value = true else backNavigation()
        }
        val areInputsValid by territoryHouseViewModel.areInputsValid.collectAsStateWithLifecycle()
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
                TerritoryHouseView(
                    territoryHousesUiModel = it,
                    sharedViewModel = appState.sharedViewModel.value,
                    territoryViewModel = territoryViewModel,
                    territoryHouseViewModel = territoryHouseViewModel
                )
                Spacer(Modifier.height(8.dp))
                SaveButtonComponent(enabled = areInputsValid, onClick = handleSaveButtonClick)
            }
        }
    }
}