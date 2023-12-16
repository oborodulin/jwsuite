package com.oborodulin.jwsuite.presentation_geo.ui.geo.regiondistrict.single

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.screen.SaveDialogScreenComponent
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
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
    defTopBarActions: @Composable RowScope.() -> Unit = {}/*,
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit*/
) {
    Timber.tag(TAG).d("RegionDistrictScreen(...) called: localityInput = %s", regionDistrictInput)
    val appState = LocalAppState.current
    val upNavigation: () -> Unit = { appState.mainNavigateUp(NavRoutes.Geo.route) }
    var topBarActions: @Composable RowScope.() -> Unit by remember { mutableStateOf(@Composable {}) }
    val onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit = { topBarActions = it }
    viewModel.dialogTitleResId.collectAsStateWithLifecycle().value?.let {
        ScaffoldComponent(
            topBarTitleResId = com.oborodulin.jwsuite.presentation.R.string.nav_item_geo,
            topBarSubtitle = stringResource(it),
            defTopBarActions = defTopBarActions,
            topBarActions = topBarActions
        ) { innerPadding ->
            SaveDialogScreenComponent(
                viewModel = viewModel,
                inputId = regionDistrictInput?.regionDistrictId,
                loadUiAction = RegionDistrictUiAction.Load(regionDistrictInput?.regionDistrictId),
                saveUiAction = RegionDistrictUiAction.Save,
                upNavigation = upNavigation,
                handleTopBarNavClick = appState.handleTopBarNavClick,
                cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_region_district,
                uniqueConstraintFailedResId = R.string.region_district_unique_constraint_error,
                /*onActionBarChange = onActionBarChange,
        onActionBarSubtitleChange = onActionBarSubtitleChange,
        onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,*/
                onTopBarActionsChange = onTopBarActionsChange,
                //onFabChange = onFabChange
                innerPadding = innerPadding
            ) { RegionDistrictView() }
        }
    }
}