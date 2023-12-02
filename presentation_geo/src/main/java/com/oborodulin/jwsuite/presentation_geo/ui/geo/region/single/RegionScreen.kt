package com.oborodulin.jwsuite.presentation_geo.ui.geo.region.single

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import com.oborodulin.home.common.ui.components.screen.SaveDialogScreenComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.RegionInput
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_geo.R
import timber.log.Timber

private const val TAG = "Geo.RegionScreen"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegionScreen(
    viewModel: RegionViewModelImpl = hiltViewModel(),
    regionInput: RegionInput? = null,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit
) {
    Timber.tag(TAG).d("RegionScreen(...) called: regionInput = %s", regionInput)
    val appState = LocalAppState.current
    val upNavigation: () -> Unit = { appState.mainNavigateUp(NavRoutes.Geo.route) }
    SaveDialogScreenComponent(
        viewModel = viewModel,
        inputId = regionInput?.regionId,
        loadUiAction = RegionUiAction.Load(regionInput?.regionId),
        saveUiAction = RegionUiAction.Save,
        upNavigation = upNavigation,
        handleTopBarNavClick = appState.handleTopBarNavClick,
        cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_region,
        uniqueConstraintFailedResId = R.string.region_unique_constraint_error,
        onActionBarSubtitleChange = onActionBarSubtitleChange,
        onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
        onTopBarActionsChange = onTopBarActionsChange
    ) { RegionView(viewModel) }
}