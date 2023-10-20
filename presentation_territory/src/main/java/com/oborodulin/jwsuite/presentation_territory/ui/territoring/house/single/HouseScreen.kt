package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.single

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
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.HouseInput
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation_territory.R
import timber.log.Timber

private const val TAG = "Territoring.HouseScreen"

@Composable
fun HouseScreen(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    //territoryViewModel: TerritoryViewModelImpl = hiltViewModel(),
    viewModel: HouseViewModelImpl = hiltViewModel(),
    houseInput: HouseInput? = null,
    paddingValues: PaddingValues,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavClickChange: (() -> Unit) -> Unit,
    onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit
) {
    Timber.tag(TAG).d("HouseScreen(...) called: houseInput = %s", houseInput)
    val backNavigation = { appState.backToBottomBarScreen() }
    val handleSaveButtonClick = {
        Timber.tag(TAG).d("HouseScreen(...): Save Button onClick...")
        // checks all errors
        viewModel.onContinueClick {
            // if success, save then backToBottomBarScreen
            viewModel.handleActionJob(
                { viewModel.submitAction(HouseUiAction.Save) },
                afterAction = backNavigation
            )
        }
    }
    LaunchedEffect(houseInput?.houseId) {
        Timber.tag(TAG).d("HouseScreen: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(HouseUiAction.Load(houseInput?.houseId))
    }
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        viewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let {
            onActionBarSubtitleChange(stringResource(it))
        }
        // Cancel Changes Confirm:
        val isUiStateChanged by viewModel.isUiStateChanged.collectAsStateWithLifecycle()
        val isCancelChangesShowAlert = rememberSaveable { mutableStateOf(false) }
        CancelChangesConfirmDialogComponent(
            isShow = isCancelChangesShowAlert,
            text = stringResource(R.string.dlg_confirm_cancel_changes_house),
            onConfirm = backNavigation
        )
        // Scaffold Hoisting:
        onTopBarNavClickChange {
            if (isUiStateChanged) isCancelChangesShowAlert.value = true else backNavigation()
        }
        val areInputsValid by viewModel.areInputsValid.collectAsStateWithLifecycle()
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
                HouseView(sharedViewModel = appState.sharedViewModel.value)
                Spacer(Modifier.height(8.dp))
                SaveButtonComponent(enabled = areInputsValid, onClick = handleSaveButtonClick)
            }
        }
    }
}