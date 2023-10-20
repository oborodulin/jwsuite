package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.handout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.dialog.alert.CancelChangesConfirmDialogComponent
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.components.HandOutButtonComponent
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesGridUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesGridViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Territoring.HandOutTerritoriesConfirmationScreen"

@Composable
fun HandOutTerritoriesConfirmationScreen(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    viewModel: TerritoriesGridViewModel,//Impl = hiltViewModel()
    paddingValues: PaddingValues,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavClickChange: (() -> Unit) -> Unit,
    onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit
) {
    Timber.tag(TAG).d("HandOutTerritoriesConfirmationScreen(...) called")
    val coroutineScope = rememberCoroutineScope()
    val handleHandOutButtonClick = {
        Timber.tag(TAG)
            .d("HandOutTerritoriesConfirmationScreen(...): Hand Out Territory Button onClick...")
        // checks all errors
        viewModel.onContinueClick {
            // if success, backToBottomBarScreen
            // https://stackoverflow.com/questions/72987545/how-to-navigate-to-another-screen-after-call-a-viemodelscope-method-in-viewmodel
            coroutineScope.launch {
                viewModel.actionsJobFlow.collectLatest { job ->
                    Timber.tag(TAG).d(
                        "HandOutTerritoriesConfirmationScreen(...): Start actionsJobFlow.collect [job = %s] for backToBottomBarScreen()",
                        job?.toString()
                    )
                    job?.join()
                    appState.backToBottomBarScreen()
                }
            }
            // hand out
            viewModel.submitAction(TerritoriesGridUiAction.HandOut)
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("HandOutTerritoriesConfirmationScreen: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(TerritoriesGridUiAction.HandOutConfirmation)
    }
    viewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let { dialogTitleResId ->
        Timber.tag(TAG).d("Collect ui state flow")
        val backNavigation = { appState.backToBottomBarScreen() }
        // Cancel Changes Confirm:
        val isUiStateChanged by viewModel.isUiStateChanged.collectAsStateWithLifecycle()
        val isCancelChangesShowAlert = rememberSaveable { mutableStateOf(false) }
        CancelChangesConfirmDialogComponent(
            isShow = isCancelChangesShowAlert,
            text = stringResource(R.string.dlg_confirm_cancel_changes_hand_out),
            onConfirm = backNavigation
        )
        // Scaffold Hoisting:
        onActionBarSubtitleChange(stringResource(dialogTitleResId))
        onTopBarNavClickChange {
            if (isUiStateChanged) isCancelChangesShowAlert.value = true else backNavigation()
        }
        val areInputsValid by viewModel.areHandOutInputsValid.collectAsStateWithLifecycle()
        onTopBarActionsChange {
            IconButton(enabled = areInputsValid, onClick = handleHandOutButtonClick) {
                Icon(Icons.Outlined.Done, null)
            }
        }
        Column(
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HandOutTerritoriesConfirmationView(
                sharedViewModel = appState.sharedViewModel.value,
                viewModel = viewModel
            )
            Spacer(Modifier.height(8.dp))
            HandOutButtonComponent(enabled = areInputsValid, onClick = handleHandOutButtonClick)
        }
    }
}