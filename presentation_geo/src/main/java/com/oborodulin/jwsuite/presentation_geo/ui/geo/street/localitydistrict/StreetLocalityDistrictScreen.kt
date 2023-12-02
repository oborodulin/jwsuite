package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.localitydistrict

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import com.oborodulin.home.common.ui.components.screen.SaveDialogScreenComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_geo.R
import timber.log.Timber

private const val TAG = "Territoring.StreetLocalityDistrictScreen"

@Composable
fun StreetLocalityDistrictScreen(
    viewModel: StreetLocalityDistrictViewModelImpl = hiltViewModel(),
    streetLocalityDistrictInput: NavigationInput.StreetLocalityDistrictInput,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit
) {
    Timber.tag(TAG)
        .d(
            "StreetLocalityDistrictScreen(...) called: streetLocalityDistrictInput = %s",
            streetLocalityDistrictInput
        )
    val appState = LocalAppState.current
    val upNavigation: () -> Unit = { appState.mainNavigateUp(NavRoutes.Geo.route) }
    SaveDialogScreenComponent(
        viewModel = viewModel,
        inputId = streetLocalityDistrictInput.streetId,
        loadUiAction = StreetLocalityDistrictUiAction.Load(streetLocalityDistrictInput.streetId),
        saveUiAction = StreetLocalityDistrictUiAction.Save,
        upNavigation = upNavigation,
        handleTopBarNavClick = appState.handleTopBarNavClick,
        cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_street_locality_district,
        onActionBarSubtitleChange = onActionBarSubtitleChange,
        onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
        onTopBarActionsChange = onTopBarActionsChange
    ) {
        StreetLocalityDistrictView(
            streetLocalityDistrictsUiModel = it,
            streetLocalityDistrictViewModel = viewModel
        )
    }
}