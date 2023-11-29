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
    viewModel: MemberRoleViewModelImpl = hiltViewModel(),
    memberRoleInput: NavigationInput.MemberRoleInput? = null,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector) -> Unit,
    onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit
) {
    Timber.tag(TAG).d("MemberRoleScreen(...) called: memberRoleInput = %s", memberRoleInput)
    val appState = LocalAppState.current
    val upNavigation: () -> Unit = { appState.backToBottomBarScreen() }
    SaveDialogScreenComponent(
        viewModel = viewModel,
        inputId = memberRoleInput?.memberRoleId,
        loadUiAction = MemberRoleUiAction.Load(memberRoleInput?.memberRoleId),
        saveUiAction = MemberRoleUiAction.Save,
        upNavigation = upNavigation,
        handleTopBarNavClick = appState.handleTopBarNavClick,
        cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_member_role,
        onActionBarSubtitleChange = onActionBarSubtitleChange,
        onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
        onTopBarActionsChange = onTopBarActionsChange
    ) { MemberRoleView(appState.sharedViewModel.value) }
}