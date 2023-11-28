package com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.single

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.screen.SaveDialogScreenComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.MicrodistrictInput
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_geo.R
import timber.log.Timber

private const val TAG = "Geo.MicrodistrictScreen"

@Composable
fun MicrodistrictScreen(
    viewModel: MicrodistrictViewModelImpl = hiltViewModel(),
    microdistrictInput: MicrodistrictInput? = null,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector) -> Unit,
    onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit
) {
    Timber.tag(TAG)
        .d("MicrodistrictScreen(...) called: microdistrictInput = %s", microdistrictInput)
    val appState = LocalAppState.current
    val upNavigation: () -> Unit = { appState.mainNavigateUp(NavRoutes.Geo.route) }
    val isUiStateChanged by viewModel.isUiStateChanged.collectAsStateWithLifecycle()
    val isCancelChangesShowAlert = rememberSaveable { mutableStateOf(false) }
    appState.handleTopBarNavClick.value =
        { if (isUiStateChanged) isCancelChangesShowAlert.value = true else upNavigation() }
    SaveDialogScreenComponent(
        viewModel = viewModel,
        inputId = microdistrictInput?.microdistrictId,
        loadUiAction = MicrodistrictUiAction.Load(microdistrictInput?.microdistrictId),
        saveUiAction = MicrodistrictUiAction.Save,
        upNavigation = upNavigation,
        isCancelChangesShowAlert = isCancelChangesShowAlert,
        cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_microdistrict,
        uniqueConstraintFailedResId = R.string.microdistrict_unique_constraint_error,
        onActionBarSubtitleChange = onActionBarSubtitleChange,
        onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
        onTopBarActionsChange = onTopBarActionsChange
    ) { MicrodistrictView() }
}