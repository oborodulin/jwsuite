package com.oborodulin.jwsuite.presentation.ui.appsetting

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.oborodulin.home.common.ui.components.screen.SaveDialogScreenComponent
import com.oborodulin.jwsuite.domain.util.MemberRoleType
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation.ui.model.LocalSession
import com.oborodulin.jwsuite.presentation.ui.session.SessionUiSingleEvent
import com.oborodulin.jwsuite.presentation.ui.session.SessionViewModel
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Presentation.AppSettingScreen"

@Composable
fun AppSettingScreen(
    appSettingViewModel: AppSettingViewModelImpl = hiltViewModel(),
    sessionViewModel: SessionViewModel, //Impl = hiltViewModel(),
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarTitleChange: (String) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit
) {
    Timber.tag(TAG).d("AppSettingScreen(...) called")
    val appState = LocalAppState.current
    val session = LocalSession.current
    val upNavigation: () -> Unit = { appState.mainNavigateUp() } //backToBottomBarScreen() }
    onActionBarTitleChange(stringResource(R.string.nav_item_settings))
    onActionBarSubtitleChange("")
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("AppSettingScreen -> LaunchedEffect(Unit)")
        appSettingViewModel.submitAction(AppSettingUiAction.Load)
    }
    SaveDialogScreenComponent(
        viewModel = appSettingViewModel,
        loadUiAction = AppSettingUiAction.Load,
        saveUiAction = AppSettingUiAction.Save,
        upNavigation = upNavigation,
        handleTopBarNavClick = appState.handleTopBarNavClick,
        isControlsShow = session.containsRole(MemberRoleType.TERRITORIES),
        cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_settings,
        onActionBarChange = onActionBarChange,
        onActionBarSubtitleChange = onActionBarSubtitleChange,
        onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
        onTopBarActionsChange = onTopBarActionsChange,
        onFabChange = onFabChange
    ) {
        AppSettingView(
            appSettingsUiModel = it,
            appSettingViewModel = appSettingViewModel,
            sessionViewModel = sessionViewModel
        )
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("AppSettingScreen -> LaunchedEffect() BEFORE collect ui state flow")
        sessionViewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is SessionUiSingleEvent.OpenSignupScreen -> {
                    appState.rootNavController.navigate(it.navRoute) {
                        popUpTo(it.navRoute) {
                            inclusive = true
                        }
                    }
                }

                else -> {}
            }
        }
    }
}