package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.localitydistrict

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.screen.SaveDialogScreenComponent
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
    onTopBarNavImageVectorChange: (ImageVector) -> Unit,
    onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit
) {
    Timber.tag(TAG)
        .d(
            "StreetLocalityDistrictScreen(...) called: streetLocalityDistrictInput = %s",
            streetLocalityDistrictInput
        )
    val appState = LocalAppState.current
    val upNavigation: () -> Unit = { appState.mainNavigateUp() }
    val isUiStateChanged by viewModel.isUiStateChanged.collectAsStateWithLifecycle()
    val isCancelChangesShowAlert = rememberSaveable { mutableStateOf(false) }
    appState.handleTopBarNavClick.value =
        { if (isUiStateChanged) isCancelChangesShowAlert.value = true else upNavigation() }
    SaveDialogScreenComponent(
        viewModel = viewModel,
        inputId = streetLocalityDistrictInput.streetId,
        loadUiAction = StreetLocalityDistrictUiAction.Load(streetLocalityDistrictInput.streetId),
        saveUiAction = StreetLocalityDistrictUiAction.Save,
        upNavigation = upNavigation,
        isCancelChangesShowAlert = isCancelChangesShowAlert,
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