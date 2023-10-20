package com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.territory

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
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryRoomInput
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModel
import timber.log.Timber

private const val TAG = "Territoring.TerritoryRoomScreen"

@Composable
fun TerritoryRoomScreen(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    territoryViewModel: TerritoryViewModel,
    territoryRoomViewModel: TerritoryRoomViewModelImpl = hiltViewModel(),
    territoryRoomInput: TerritoryRoomInput? = null,
    paddingValues: PaddingValues,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavClickChange: (() -> Unit) -> Unit,
    onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit
) {
    Timber.tag(TAG)
        .d("TerritoryRoomScreen(...) called: territoryRoomInput = %s", territoryRoomInput)

    val handleSaveButtonClick = {
        // checks all errors
        territoryRoomViewModel.onContinueClick {
            Timber.tag(TAG).d("TerritoryRoomScreen(...): Save Button onClick...")
            // if success, save then backToBottomBarScreen
            territoryRoomViewModel.handleActionJob(
                { territoryRoomViewModel.submitAction(TerritoryRoomUiAction.Save) },
                { appState.commonNavigateUp() })
        }
    }
    LaunchedEffect(territoryRoomInput?.territoryId) {
        Timber.tag(TAG).d("TerritoryRoomScreen: LaunchedEffect() BEFORE collect ui state flow")
        territoryRoomInput?.let {
            territoryRoomViewModel.submitAction(TerritoryRoomUiAction.Load(it.territoryId))
        }
    }
    territoryRoomViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        territoryRoomViewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let {
            onActionBarSubtitleChange(stringResource(it))
        }
        val backNavigation: () -> Unit = { appState.commonNavigateUp() }
        // Cancel Changes Confirm:
        val isUiStateChanged by territoryRoomViewModel.isUiStateChanged.collectAsStateWithLifecycle()
        val isCancelChangesShowAlert = rememberSaveable { mutableStateOf(false) }
        CancelChangesConfirmDialogComponent(
            isShow = isCancelChangesShowAlert,
            text = stringResource(R.string.dlg_confirm_cancel_changes_territory_room),
            onConfirm = backNavigation
        )
        // Scaffold Hoisting:
        onTopBarNavClickChange {
            if (isUiStateChanged) isCancelChangesShowAlert.value = true else backNavigation()
        }
        val areInputsValid by territoryRoomViewModel.areInputsValid.collectAsStateWithLifecycle()
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
                TerritoryRoomView(
                    territoryRoomsUiModel = it,
                    sharedViewModel = appState.sharedViewModel.value,
                    territoryViewModel = territoryViewModel,
                    territoryRoomViewModel = territoryRoomViewModel
                )
                Spacer(Modifier.height(8.dp))
                SaveButtonComponent(enabled = areInputsValid, onClick = handleSaveButtonClick)
            }
        }
    }
}