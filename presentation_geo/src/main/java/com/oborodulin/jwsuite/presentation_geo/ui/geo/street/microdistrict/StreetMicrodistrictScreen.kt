package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.microdistrict

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

private const val TAG = "Territoring.StreetMicrodistrictScreen"

@Composable
fun StreetMicrodistrictScreen(
    viewModel: StreetMicrodistrictViewModelImpl = hiltViewModel(),
    streetMicrodistrictInput: NavigationInput.StreetMicrodistrictInput,
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit
) {
    Timber.tag(TAG)
        .d(
            "StreetMicrodistrictScreen(...) called: streetMicrodistrictInput = %s",
            streetMicrodistrictInput
        )
    val appState = LocalAppState.current
    val upNavigation: () -> Unit = { appState.mainNavigateUp(NavRoutes.Geo.route) }
    SaveDialogScreenComponent(
        viewModel = viewModel,
        inputId = streetMicrodistrictInput.streetId,
        loadUiAction = StreetMicrodistrictUiAction.Load(streetMicrodistrictInput.streetId),
        saveUiAction = StreetMicrodistrictUiAction.Save,
        upNavigation = upNavigation,
        handleTopBarNavClick = appState.handleTopBarNavClick,
        cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_street_microdistrict,
        onActionBarChange = onActionBarChange,
        onActionBarSubtitleChange = onActionBarSubtitleChange,
        onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
        onTopBarActionsChange = onTopBarActionsChange,
        onFabChange = onFabChange
    ) {
        StreetMicrodistrictView(
            streetMicrodistrictsUiModel = it,
            streetMicrodistrictViewModel = viewModel
        )
    }
}