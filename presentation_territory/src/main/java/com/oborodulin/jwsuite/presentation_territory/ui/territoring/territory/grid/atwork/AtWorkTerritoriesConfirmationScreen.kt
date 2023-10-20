package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.atwork

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.dialog.alert.CancelChangesConfirmDialogComponent
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesGridUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.grid.TerritoriesGridViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Territoring.AtWorkTerritoriesConfirmationScreen"

@Composable
fun AtWorkTerritoriesConfirmationScreen(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    viewModel: TerritoriesGridViewModel,//Impl = hiltViewModel()
    paddingValues: PaddingValues,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavClickChange: (() -> Unit) -> Unit,
    onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit
) {
    Timber.tag(TAG).d("AtWorkTerritoriesConfirmationScreen(...) called")
    val coroutineScope = rememberCoroutineScope()
    val handleProcessButtonClick = {
        Timber.tag(TAG)
            .d("AtWorkTerritoriesConfirmationScreen(...): Hand Out Territory Button onClick...")
        // checks all errors
        viewModel.onContinueClick {
            // if success, backToBottomBarScreen
            // https://stackoverflow.com/questions/72987545/how-to-navigate-to-another-screen-after-call-a-viemodelscope-method-in-viewmodel
            coroutineScope.launch {
                viewModel.actionsJobFlow.collectLatest { job ->
                    Timber.tag(TAG).d(
                        "AtWorkTerritoriesConfirmationScreen(...): Start actionsJobFlow.collect [job = %s] for backToBottomBarScreen()",
                        job?.toString()
                    )
                    job?.join()
                    appState.backToBottomBarScreen()
                }
            }
            // process
            viewModel.submitAction(TerritoriesGridUiAction.Process)
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG)
            .d("AtWorkTerritoriesConfirmationScreen: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(TerritoriesGridUiAction.ProcessConfirmation)
    }
    viewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let { dialogTitleResId ->
        Timber.tag(TAG).d("Collect ui state flow")
        val backNavigation = { appState.backToBottomBarScreen() }
        // Cancel Changes Confirm:
        val isUiStateChanged by viewModel.isUiStateChanged.collectAsStateWithLifecycle()
        val isCancelChangesShowAlert = rememberSaveable { mutableStateOf(false) }
        CancelChangesConfirmDialogComponent(
            isShow = isCancelChangesShowAlert,
            text = stringResource(R.string.dlg_confirm_cancel_changes_at_work),
            onConfirm = backNavigation
        )
        // Scaffold Hoisting:
        onActionBarSubtitleChange(stringResource(dialogTitleResId))
        onTopBarNavClickChange {
            if (isUiStateChanged) isCancelChangesShowAlert.value = true else backNavigation()
        }
        val areInputsValid by viewModel.areAtWorkProcessInputsValid.collectAsStateWithLifecycle()
        onTopBarActionsChange {
            IconButton(enabled = areInputsValid, onClick = handleProcessButtonClick) {
                Icon(Icons.Outlined.Done, null)
            }
        }
        Column(
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AtWorkTerritoriesConfirmationView(
                sharedViewModel = appState.sharedViewModel.value, viewModel = viewModel
            )
        }
    }
}