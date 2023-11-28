package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.microdistrict

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
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_geo.R
import timber.log.Timber

private const val TAG = "Territoring.StreetMicrodistrictScreen"

@Composable
fun StreetMicrodistrictScreen(
    viewModel: StreetMicrodistrictViewModelImpl = hiltViewModel(),
    streetMicrodistrictInput: NavigationInput.StreetMicrodistrictInput,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector) -> Unit,
    onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit
) {
    Timber.tag(TAG)
        .d(
            "StreetMicrodistrictScreen(...) called: streetMicrodistrictInput = %s",
            streetMicrodistrictInput
        )
    val appState = LocalAppState.current
    val upNavigation: () -> Unit = { appState.mainNavigateUp(NavRoutes.Geo.route) }
    val isUiStateChanged by viewModel.isUiStateChanged.collectAsStateWithLifecycle()
    val isCancelChangesShowAlert = rememberSaveable { mutableStateOf(false) }
    appState.handleTopBarNavClick.value =
        { if (isUiStateChanged) isCancelChangesShowAlert.value = true else upNavigation() }
    SaveDialogScreenComponent(
        viewModel = viewModel,
        inputId = streetMicrodistrictInput.streetId,
        loadUiAction = StreetMicrodistrictUiAction.Load(streetMicrodistrictInput.streetId),
        saveUiAction = StreetMicrodistrictUiAction.Save,
        upNavigation = upNavigation,
        isCancelChangesShowAlert = isCancelChangesShowAlert,
        cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_street_microdistrict,
        onActionBarSubtitleChange = onActionBarSubtitleChange,
        onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
        onTopBarActionsChange = onTopBarActionsChange
    ) {
        StreetMicrodistrictView(
            streetMicrodistrictsUiModel = it,
            streetMicrodistrictViewModel = viewModel
        )
    }
}