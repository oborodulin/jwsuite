package com.oborodulin.jwsuite.presentation_dashboard.ui.dashboarding.setting

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.oborodulin.home.common.ui.components.buttons.SaveButtonComponent
import com.oborodulin.home.common.ui.components.screen.DialogScreenComponent
import com.oborodulin.jwsuite.domain.types.MemberRoleType
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation.ui.model.LocalSession
import com.oborodulin.jwsuite.presentation.ui.session.SessionUiSingleEvent
import com.oborodulin.jwsuite.presentation.ui.session.SessionViewModel
import com.oborodulin.jwsuite.presentation_dashboard.R
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Dashboarding.DashboardSettingScreen"

@Composable
fun DashboardSettingScreen(
    appSettingViewModel: DashboardSettingViewModelImpl = hiltViewModel(),
    sessionViewModel: SessionViewModel, //Impl = hiltViewModel(),
    defTopBarActions: @Composable RowScope.() -> Unit = {}
) {
    Timber.tag(TAG).d("DashboardSettingScreen(...) called")
    val appState = LocalAppState.current
    val session = LocalSession.current
    val upNavigation: () -> Unit = { appState.mainNavigateUp() }
    /*LaunchedEffect(Unit) {
        Timber.tag(TAG).d("DashboardSettingScreen -> LaunchedEffect(Unit)")
        appSettingViewModel.submitAction(DashboardSettingUiAction.Load)
    }*/
    var topBarActions: @Composable RowScope.() -> Unit by remember { mutableStateOf(@Composable {}) }
    val onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit = { topBarActions = it }
    var actionBarSubtitle by rememberSaveable { mutableStateOf("") }
    val onActionBarSubtitleChange: (String) -> Unit = { actionBarSubtitle = it }
    ScaffoldComponent(
        topBarTitle = stringResource(com.oborodulin.jwsuite.presentation.R.string.nav_item_dashboard_settings),
        topBarSubtitle = actionBarSubtitle,
        defTopBarActions = defTopBarActions,
        topBarActions = topBarActions
    ) { innerPadding ->
        DialogScreenComponent(
            viewModel = appSettingViewModel,
            loadUiAction = DashboardSettingUiAction.Load,
            confirmUiAction = DashboardSettingUiAction.Save,
            upNavigation = upNavigation,
            handleTopBarNavClick = appState.handleTopBarNavClick,
            isControlsShow = session.containsRole(MemberRoleType.TERRITORIES),
            cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_settings,
            confirmButton = { areValid, handleSaveButtonClick ->
                SaveButtonComponent(enabled = areValid, onClick = handleSaveButtonClick)
            },
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarActionsChange = onTopBarActionsChange,
            innerPadding = innerPadding
        ) { uiModel, _, _, _ ->
            DashboardSettingView(
                //modifier = Modifier.verticalScroll(rememberScrollState()),
                dashboardSettingsUiModel = uiModel,
                dashboardSettingViewModel = appSettingViewModel,
                sessionViewModel = sessionViewModel
            )
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("DashboardSettingScreen -> LaunchedEffect()")
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