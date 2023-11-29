package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.single

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.screen.SaveDialogScreenComponent
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.GroupInput
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_congregation.R
import timber.log.Timber

private const val TAG = "Congregating.GroupScreen"

@Composable
fun GroupScreen(
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    viewModel: GroupViewModelImpl = hiltViewModel(),
    groupInput: GroupInput? = null,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector) -> Unit,
    onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit
) {
    Timber.tag(TAG).d("GroupScreen(...) called: groupInput = %s", groupInput)
    val appState = LocalAppState.current
    val upNavigation: () -> Unit = { appState.backToBottomBarScreen() }

    val currentCongregation =
        appState.sharedViewModel.value?.sharedFlow?.collectAsStateWithLifecycle()?.value
    LaunchedEffect(groupInput?.groupId) {
        Timber.tag(TAG).d("GroupScreen -> LaunchedEffect() BEFORE collect ui state flow")
        currentCongregation?.itemId?.let { congregationId ->
            viewModel.onInsert {
                viewModel.submitAction(GroupUiAction.GetNextGroupNum(congregationId))
            }
        }
    }
    SaveDialogScreenComponent(
        viewModel = viewModel,
        inputId = groupInput?.groupId,
        loadUiAction = GroupUiAction.Load(groupInput?.groupId),
        saveUiAction = GroupUiAction.Save,
        upNavigation = upNavigation,
        handleTopBarNavClick = appState.handleTopBarNavClick,
        cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_group,
        uniqueConstraintFailedResId = R.string.group_unique_constraint_error,
        onActionBarSubtitleChange = onActionBarSubtitleChange,
        onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
        onTopBarActionsChange = onTopBarActionsChange
    ) { GroupView(appState.sharedViewModel.value) }
}
