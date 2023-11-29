package com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.single

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
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
    SaveDialogScreenComponent(
        viewModel = viewModel,
        inputId = microdistrictInput?.microdistrictId,
        loadUiAction = MicrodistrictUiAction.Load(microdistrictInput?.microdistrictId),
        saveUiAction = MicrodistrictUiAction.Save,
        upNavigation = upNavigation,
        handleTopBarNavClick = appState.handleTopBarNavClick,
        cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_microdistrict,
        uniqueConstraintFailedResId = R.string.microdistrict_unique_constraint_error,
        onActionBarSubtitleChange = onActionBarSubtitleChange,
        onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
        onTopBarActionsChange = onTopBarActionsChange
    ) { MicrodistrictView() }
}