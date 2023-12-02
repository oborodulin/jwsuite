package com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.single

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import com.oborodulin.home.common.ui.components.screen.SaveDialogScreenComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.LocalityDistrictInput
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_geo.R
import timber.log.Timber

private const val TAG = "Geo.LocalityDistrictScreen"

@Composable
fun LocalityDistrictScreen(
    viewModel: LocalityDistrictViewModelImpl = hiltViewModel(),
    localityDistrictInput: LocalityDistrictInput? = null,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit
) {
    Timber.tag(TAG)
        .d("LocalityDistrictScreen(...) called: localityDistrictInput = %s", localityDistrictInput)
    val appState = LocalAppState.current
    val upNavigation: () -> Unit = { appState.mainNavigateUp(NavRoutes.Geo.route) }
    SaveDialogScreenComponent(
        viewModel = viewModel,
        inputId = localityDistrictInput?.localityDistrictId,
        loadUiAction = LocalityDistrictUiAction.Load(localityDistrictInput?.localityDistrictId),
        saveUiAction = LocalityDistrictUiAction.Save,
        upNavigation = upNavigation,
        handleTopBarNavClick = appState.handleTopBarNavClick,
        cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_locality_district,
        uniqueConstraintFailedResId = R.string.locality_district_unique_constraint_error,
        onActionBarSubtitleChange = onActionBarSubtitleChange,
        onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
        onTopBarActionsChange = onTopBarActionsChange
    ) { LocalityDistrictView() }
}