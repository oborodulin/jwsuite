package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorycategory.single

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
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryCategoryInput
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_territory.R
import timber.log.Timber

private const val TAG = "Territoring.TerritoryCategoryScreen"

@Composable
fun TerritoryCategoryScreen(
    viewModel: TerritoryCategoryViewModelImpl = hiltViewModel(),
    territoryCategoryInput: TerritoryCategoryInput? = null,
    defTopBarActions: @Composable RowScope.() -> Unit = {}
) {
    Timber.tag(TAG)
        .d(
            "TerritoryCategoryScreen(...) called: territoryCategoryInput = %s",
            territoryCategoryInput
        )
    val appState = LocalAppState.current
    val upNavigation = { appState.mainNavigateUp() } //backToBottomBarScreen() }
    var topBarActions: @Composable RowScope.() -> Unit by remember { mutableStateOf(@Composable {}) }
    val onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit = { topBarActions = it }
    var actionBarSubtitle by rememberSaveable { mutableStateOf("") }
    val onActionBarSubtitleChange: (String) -> Unit = { actionBarSubtitle = it }
    ScaffoldComponent(
        topBarTitleResId = NavRoutes.Territoring.titleResId,
        navRoute = NavRoutes.TerritoryCategory,
        topBarSubtitle = actionBarSubtitle,
        defTopBarActions = defTopBarActions,
        topBarActions = topBarActions
    ) { innerPadding ->
        SaveDialogScreenComponent(
            viewModel = viewModel,
            inputId = territoryCategoryInput?.territoryCategoryId,
            loadUiAction = TerritoryCategoryUiAction.Load(territoryCategoryInput?.territoryCategoryId),
            saveUiAction = TerritoryCategoryUiAction.Save,
            upNavigation = upNavigation,
            handleTopBarNavClick = appState.handleTopBarNavClick,
            cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_territory_category,
            uniqueConstraintFailedResId = R.string.territory_category_unique_constraint_error,
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarActionsChange = onTopBarActionsChange,
            innerPadding = innerPadding
        ) { _, _, _, handleSaveAction ->
            TerritoryCategoryView(
                viewModel = viewModel,
                handleSaveAction = handleSaveAction
            )
        }
    }
}