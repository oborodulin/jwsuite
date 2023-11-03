package com.oborodulin.jwsuite.presentation_territory.ui.territoring.house

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.buttons.SaveButtonComponent
import com.oborodulin.home.common.ui.components.dialog.alert.CancelChangesConfirmDialogComponent
import com.oborodulin.home.common.ui.state.CommonScreen
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
    territoryHouseInput: TerritoryHouseInput? = null,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector) -> Unit,
    onTopBarNavClickChange: (() -> Unit) -> Unit,
    onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit
) {
    Timber.tag(TAG)
        .d("TerritoryHouseScreen(...) called: territoryHouseInput = %s", territoryHouseInput)
    val appState = LocalAppState.current
    val upNavigation: () -> Unit = { appState.mainNavigateUp() }
    val handleSaveButtonClick = {
        // checks all errors
        territoryHouseViewModel.onContinueClick {
            Timber.tag(TAG).d("TerritoryHouseScreen(...): Save Button onClick...")
            // if success, save then backToBottomBarScreen
            territoryHouseViewModel.handleActionJob(
                { territoryHouseViewModel.submitAction(TerritoryHouseUiAction.Save) },
                afterAction = upNavigation
            )
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
        // Cancel Changes Confirm:
        val isUiStateChanged by territoryHouseViewModel.isUiStateChanged.collectAsStateWithLifecycle()
        val isCancelChangesShowAlert = rememberSaveable { mutableStateOf(false) }
        CancelChangesConfirmDialogComponent(
            isShow = isCancelChangesShowAlert,
            text = stringResource(R.string.dlg_confirm_cancel_changes_territory_house),
            onConfirm = upNavigation
        )
        // Scaffold Hoisting:
        onTopBarNavImageVectorChange(Icons.Outlined.ArrowBack)
        onTopBarNavClickChange {
            if (isUiStateChanged) isCancelChangesShowAlert.value = true else upNavigation()
        }
        val areInputsValid by territoryHouseViewModel.areInputsValid.collectAsStateWithLifecycle()
        onTopBarActionsChange {
            IconButton(enabled = areInputsValid, onClick = handleSaveButtonClick) {
                Icon(Icons.Outlined.Done, null)
            }
        }
        CommonScreen(state = state) {
            Column(
                modifier = Modifier.fillMaxSize(),
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