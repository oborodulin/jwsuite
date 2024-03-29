package com.oborodulin.jwsuite.presentation_geo.ui.geo.country.single

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

private const val TAG = "Geo.CountryScreen"

@Composable
fun CountryScreen(
    viewModel: CountryViewModelImpl = hiltViewModel(),
    countryInput: NavigationInput.CountryInput? = null,
    defTopBarActions: @Composable RowScope.() -> Unit = {}
) {
    Timber.tag(TAG).d("CountryScreen(...) called: countryInput = %s", countryInput)
    val appState = LocalAppState.current
    val upNavigation: () -> Unit = { appState.mainNavigateUp(NavRoutes.Geo.route) }
    var topBarActions: @Composable RowScope.() -> Unit by remember { mutableStateOf(@Composable {}) }
    val onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit = { topBarActions = it }
    var actionBarSubtitle by rememberSaveable { mutableStateOf("") }
    val onActionBarSubtitleChange: (String) -> Unit = { actionBarSubtitle = it }
    /*LaunchedEffect(countryInput?.countryId) {
        if (LOG_FLOW_ACTION) {
            Timber.tag(TAG)
                .d(
                    "DialogScreenComponent -> LaunchedEffect(inputId): countryInput = %s",
                    countryInput
                )
        }
        viewModel.submitAction(CountryUiAction.Load(countryInput?.countryId))
    }
    val handleSaveButtonClick = {
        viewModel.onContinueClick {
            viewModel.handleActionJob({ viewModel.submitAction(CountryUiAction.Save) }) { scope ->
                upNavigation()
            }
        }
    }*/
    ScaffoldComponent(
        topBarTitleResId = NavRoutes.Geo.titleResId,
        navRoute = NavRoutes.Country,
        topBarSubtitle = actionBarSubtitle,
        defTopBarActions = defTopBarActions,
        topBarActions = topBarActions
    ) { innerPadding ->
/*        viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
            CommonScreen(paddingValues = innerPadding, state = state) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CountryView(viewModel = viewModel, handleSaveAction = handleSaveButtonClick)
                    SaveButtonComponent(enabled = true, onClick = handleSaveButtonClick)
                }
            }*/
        SaveDialogScreenComponent(
            viewModel = viewModel,
            inputId = countryInput?.countryId,
            loadUiAction = CountryUiAction.Load(countryInput?.countryId),
            saveUiAction = CountryUiAction.Save,
            upNavigation = upNavigation,
            handleTopBarNavClick = appState.handleTopBarNavClick,
            cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_country,
            uniqueConstraintFailedResId = R.string.country_unique_constraint_error,
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarActionsChange = onTopBarActionsChange,
            innerPadding = innerPadding
        ) { _, _, _, handleSaveAction ->
            CountryView(viewModel = viewModel, handleSaveAction = handleSaveAction)
        }
    }
}