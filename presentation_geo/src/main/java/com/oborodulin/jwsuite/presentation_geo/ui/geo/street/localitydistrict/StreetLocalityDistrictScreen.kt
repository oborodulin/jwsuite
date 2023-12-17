package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.localitydistrict

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.oborodulin.home.common.ui.components.screen.SaveDialogScreenComponent
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
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
    defTopBarActions: @Composable RowScope.() -> Unit = {}/*,
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit*/
) {
    Timber.tag(TAG)
        .d(
            "StreetLocalityDistrictScreen(...) called: streetLocalityDistrictInput = %s",
            streetLocalityDistrictInput
        )
    val appState = LocalAppState.current
    val upNavigation: () -> Unit = { appState.mainNavigateUp(NavRoutes.Geo.route) }
    var topBarActions: @Composable RowScope.() -> Unit by remember { mutableStateOf(@Composable {}) }
    val onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit = { topBarActions = it }
    var actionBarSubtitle by rememberSaveable { mutableStateOf("") }
    val onActionBarSubtitleChange: (String) -> Unit = { actionBarSubtitle = it }
    ScaffoldComponent(
        topBarTitleResId = com.oborodulin.jwsuite.presentation.R.string.nav_item_geo,
        topBarSubtitle = actionBarSubtitle,
        defTopBarActions = defTopBarActions,
        topBarActions = topBarActions
    ) { innerPadding ->
        SaveDialogScreenComponent(
            viewModel = viewModel,
            inputId = streetLocalityDistrictInput.streetId,
            loadUiAction = StreetLocalityDistrictUiAction.Load(streetLocalityDistrictInput.streetId),
            saveUiAction = StreetLocalityDistrictUiAction.Save,
            upNavigation = upNavigation,
            handleTopBarNavClick = appState.handleTopBarNavClick,
            cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_street_locality_district,
            /*onActionBarChange = onActionBarChange,
    onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,*/
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarActionsChange = onTopBarActionsChange,
            //onFabChange = onFabChange
            innerPadding = innerPadding
        ) { uiModel ->
            StreetLocalityDistrictView(
                streetLocalityDistrictsUiModel = uiModel,
                streetLocalityDistrictViewModel = viewModel
            )
        }
    }
}