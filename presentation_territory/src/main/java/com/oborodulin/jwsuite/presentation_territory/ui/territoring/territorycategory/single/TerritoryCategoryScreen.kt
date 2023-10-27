package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorycategory.single

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.buttons.SaveButtonComponent
import com.oborodulin.home.common.ui.components.dialog.alert.CancelChangesConfirmDialogComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryCategoryInput
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation_territory.R
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Territoring.TerritoryCategoryScreen"

@Composable
fun TerritoryCategoryScreen(
    appState: AppState,
    viewModel: TerritoryCategoryViewModelImpl = hiltViewModel(),
    territoryCategoryInput: TerritoryCategoryInput? = null,
    paddingValues: PaddingValues,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector) -> Unit,
    onTopBarNavClickChange: (() -> Unit) -> Unit,
    onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit
) {
    Timber.tag(TAG)
        .d(
            "TerritoryCategoryScreen(...) called: territoryCategoryInput = %s",
            territoryCategoryInput
        )
    val coroutineScope = rememberCoroutineScope()
    val handleSaveButtonClick = {
        viewModel.onContinueClick {
            Timber.tag(TAG)
                .d("TerritoryCategoryScreen(...): Start coroutineScope.launch")
            coroutineScope.launch {
                viewModel.actionsJobFlow.collect {
                    Timber.tag(TAG).d(
                        "TerritoryCategoryScreen(...): Start actionsJobFlow.collect [job = %s]",
                        it?.toString()
                    )
                    it?.join()
                    appState.backToBottomBarScreen()
                }
            }
            viewModel.submitAction(TerritoryCategoryUiAction.Save)
            Timber.tag(TAG)
                .d("TerritoryCategoryScreen(...): onSubmit() executed")
        }
    }
    LaunchedEffect(territoryCategoryInput?.territoryCategoryId) {
        Timber.tag(TAG).d("TerritoryCategoryScreen: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(TerritoryCategoryUiAction.Load(territoryCategoryInput?.territoryCategoryId))
    }
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        viewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let {
            onActionBarSubtitleChange(stringResource(it))
        }
        val backNavigation = { appState.backToBottomBarScreen() }
        // Cancel Changes Confirm:
        val isUiStateChanged by viewModel.isUiStateChanged.collectAsStateWithLifecycle()
        val isCancelChangesShowAlert = rememberSaveable { mutableStateOf(false) }
        CancelChangesConfirmDialogComponent(
            isShow = isCancelChangesShowAlert,
            text = stringResource(R.string.dlg_confirm_cancel_changes_territory_category),
            onConfirm = backNavigation
        )
        // Scaffold Hoisting:
        onTopBarNavImageVectorChange(Icons.Outlined.ArrowBack)
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
            TerritoryCategoryView(viewModel)
            Spacer(Modifier.height(8.dp))
            SaveButtonComponent(enabled = areInputsValid, onClick = handleSaveButtonClick)
        }
    }
}