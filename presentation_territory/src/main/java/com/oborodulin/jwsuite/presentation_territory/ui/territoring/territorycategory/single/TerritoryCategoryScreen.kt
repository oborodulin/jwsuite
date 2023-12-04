package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorycategory.single

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import com.oborodulin.home.common.ui.components.screen.SaveDialogScreenComponent
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryCategoryInput
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_territory.R
import timber.log.Timber

private const val TAG = "Territoring.TerritoryCategoryScreen"

@Composable
fun TerritoryCategoryScreen(
    viewModel: TerritoryCategoryViewModelImpl = hiltViewModel(),
    territoryCategoryInput: TerritoryCategoryInput? = null,
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit
) {
    Timber.tag(TAG)
        .d(
            "TerritoryCategoryScreen(...) called: territoryCategoryInput = %s",
            territoryCategoryInput
        )
    val appState = LocalAppState.current
    val upNavigation = { appState.backToBottomBarScreen() }
    SaveDialogScreenComponent(
        viewModel = viewModel,
        inputId = territoryCategoryInput?.territoryCategoryId,
        loadUiAction = TerritoryCategoryUiAction.Load(territoryCategoryInput?.territoryCategoryId),
        saveUiAction = TerritoryCategoryUiAction.Save,
        upNavigation = upNavigation,
        handleTopBarNavClick = appState.handleTopBarNavClick,
        cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_territory_category,
        uniqueConstraintFailedResId = R.string.territory_category_unique_constraint_error,
        onActionBarChange = onActionBarChange,
        onActionBarSubtitleChange = onActionBarSubtitleChange,
        onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
        onTopBarActionsChange = onTopBarActionsChange,
        onFabChange = onFabChange
    ) { TerritoryCategoryView(viewModel) }
}