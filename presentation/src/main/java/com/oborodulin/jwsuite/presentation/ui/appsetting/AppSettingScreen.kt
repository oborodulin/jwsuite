package com.oborodulin.jwsuite.presentation.ui.appsetting

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.oborodulin.home.common.ui.components.screen.SaveDialogScreenComponent
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import timber.log.Timber

private const val TAG = "Presentation.AppSettingScreen"

@Composable
fun AppSettingScreen(
    viewModel: AppSettingViewModelImpl = hiltViewModel(),
    onActionBarTitleChange: (String) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector) -> Unit,
    onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit
) {
    Timber.tag(TAG).d("AppSettingScreen(...) called")
    val appState = LocalAppState.current
    val upNavigation: () -> Unit = { appState.backToBottomBarScreen() }
    onActionBarTitleChange(stringResource(R.string.nav_item_settings))
    onActionBarSubtitleChange("")
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("AppSettingScreen -> LaunchedEffect(Unit)")
        viewModel.submitAction(AppSettingUiAction.Load)
    }
    SaveDialogScreenComponent(
        viewModel = viewModel,
        loadUiAction = AppSettingUiAction.Load,
        saveUiAction = AppSettingUiAction.Save,
        upNavigation = upNavigation,
        handleTopBarNavClick = appState.handleTopBarNavClick,
        cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_settings,
        onActionBarSubtitleChange = onActionBarSubtitleChange,
        onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
        onTopBarActionsChange = onTopBarActionsChange
    ) { AppSettingView(appSettingsUiModel = it, viewModel) }
}