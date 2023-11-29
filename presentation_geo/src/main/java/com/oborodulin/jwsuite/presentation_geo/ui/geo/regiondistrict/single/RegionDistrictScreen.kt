package com.oborodulin.jwsuite.presentation_geo.ui.geo.regiondistrict.single

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import com.oborodulin.home.common.ui.components.screen.SaveDialogScreenComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.RegionDistrictInput
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_geo.R
import timber.log.Timber

private const val TAG = "Geo.RegionDistrictScreen"

@Composable
fun RegionDistrictScreen(
    viewModel: RegionDistrictViewModelImpl = hiltViewModel(),
    regionDistrictInput: RegionDistrictInput? = null,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector) -> Unit,
    onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit
) {
    Timber.tag(TAG).d("RegionDistrictScreen(...) called: localityInput = %s", regionDistrictInput)
    val appState = LocalAppState.current
    val upNavigation: () -> Unit = { appState.mainNavigateUp(NavRoutes.Geo.route) }
    SaveDialogScreenComponent(
        viewModel = viewModel,
        inputId = regionDistrictInput?.regionDistrictId,
        loadUiAction = RegionDistrictUiAction.Load(regionDistrictInput?.regionDistrictId),
        saveUiAction = RegionDistrictUiAction.Save,
        upNavigation = upNavigation,
        handleTopBarNavClick = appState.handleTopBarNavClick,
        cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_region_district,
        uniqueConstraintFailedResId = R.string.region_district_unique_constraint_error,
        onActionBarSubtitleChange = onActionBarSubtitleChange,
        onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
        onTopBarActionsChange = onTopBarActionsChange
    ) { RegionDistrictView() }
}