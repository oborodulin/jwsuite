package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.role.single

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import com.oborodulin.home.common.ui.components.screen.SaveDialogScreenComponent
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_congregation.R
import timber.log.Timber

private const val TAG = "Congregating.MemberRoleScreen"

@Composable
fun MemberRoleScreen(
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    memberRoleViewModel: MemberRoleViewModelImpl = hiltViewModel(),
    memberRoleInput: NavigationInput.MemberRoleInput? = null,
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit
) {
    Timber.tag(TAG).d("MemberRoleScreen(...) called: memberRoleInput = %s", memberRoleInput)
    val appState = LocalAppState.current
    val upNavigation: () -> Unit = { appState.mainNavigateUp() } //backToBottomBarScreen() }
    SaveDialogScreenComponent(
        viewModel = memberRoleViewModel,
        inputId = memberRoleInput?.memberRoleId,
        loadUiAction = MemberRoleUiAction.Load(memberRoleInput?.memberRoleId),
        saveUiAction = MemberRoleUiAction.Save,
        upNavigation = upNavigation,
        handleTopBarNavClick = appState.handleTopBarNavClick,
        cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_member_role,
        onActionBarChange = onActionBarChange,
        onActionBarSubtitleChange = onActionBarSubtitleChange,
        onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
        onTopBarActionsChange = onTopBarActionsChange,
        onFabChange = onFabChange
    ) {
        MemberRoleView()
    }
}