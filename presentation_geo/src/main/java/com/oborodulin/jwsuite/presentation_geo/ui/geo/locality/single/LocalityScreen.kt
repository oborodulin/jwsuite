package com.oborodulin.jwsuite.presentation_geo.ui.geo.locality.single

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.LocalityInput
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation_geo.R
import kotlinx.coroutines.launch
import timber.log.Timber

private const val TAG = "Geo.LocalityScreen"

@Composable
fun LocalityScreen(
    appState: AppState,
    viewModel: LocalityViewModelImpl = hiltViewModel(),
    localityInput: LocalityInput? = null,
    paddingValues: PaddingValues,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector) -> Unit,
    onTopBarNavClickChange: (() -> Unit) -> Unit,
    onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit
) {
    Timber.tag(TAG).d("LocalityScreen(...) called: localityInput = %s", localityInput)
    val coroutineScope = rememberCoroutineScope()
    val handleSaveButtonClick = {
        viewModel.onContinueClick {
            Timber.tag(TAG).d("LocalityScreen(...): Save Button onClick...")
            coroutineScope.launch {
                viewModel.actionsJobFlow.collect {
                    Timber.tag(TAG).d(
                        "LocalityScreen(...): Start actionsJobFlow.collect [job = %s]",
                        it?.toString()
                    )
                    it?.join()
                    appState.backToBottomBarScreen()
                }
            }
            viewModel.submitAction(LocalityUiAction.Save)
            Timber.tag(TAG).d("RegionScreen(...): onSubmit() executed")
        }
    }
    LaunchedEffect(localityInput?.localityId) {
        Timber.tag(TAG).d("LocalityScreen: LaunchedEffect() BEFORE collect ui state flow")
        viewModel.submitAction(LocalityUiAction.Load(localityInput?.localityId))
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
            text = stringResource(R.string.dlg_confirm_cancel_changes_locality),
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
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LocalityView()
                Spacer(Modifier.height(8.dp))
                SaveButtonComponent(enabled = areInputsValid, onClick = handleSaveButtonClick)
            }
        }
    }
}