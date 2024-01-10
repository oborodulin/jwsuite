package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.single

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.screen.SaveDialogScreenComponent
import com.oborodulin.jwsuite.presentation.components.ScaffoldComponent
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
    defTopBarActions: @Composable RowScope.() -> Unit = {}/*,
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit*/
) {
    Timber.tag(TAG).d("GroupScreen(...) called: groupInput = %s", groupInput)
    val appState = LocalAppState.current
    val upNavigation: () -> Unit = { appState.mainNavigateUp() } //backToBottomBarScreen() }

    val currentCongregation =
        appState.congregationSharedViewModel.value?.sharedFlow?.collectAsStateWithLifecycle()?.value
    LaunchedEffect(groupInput?.groupId) {
        Timber.tag(TAG).d("GroupScreen -> LaunchedEffect() BEFORE collect ui state flow")
        currentCongregation?.itemId?.let { congregationId ->
            viewModel.onInsert {
                viewModel.submitAction(GroupUiAction.GetNextGroupNum(congregationId))
            }
        }
    }
    var topBarActions: @Composable RowScope.() -> Unit by remember { mutableStateOf(@Composable {}) }
    val onTopBarActionsChange: (@Composable RowScope.() -> Unit) -> Unit = { topBarActions = it }
    var actionBarSubtitle by rememberSaveable { mutableStateOf("") }
    val onActionBarSubtitleChange: (String) -> Unit = { actionBarSubtitle = it }
    ScaffoldComponent(
        topBarSubtitle = actionBarSubtitle,
        defTopBarActions = defTopBarActions,
        topBarActions = topBarActions
    ) { innerPadding ->
        SaveDialogScreenComponent(
            viewModel = viewModel,
            inputId = groupInput?.groupId,
            loadUiAction = GroupUiAction.Load(groupInput?.groupId),
            saveUiAction = GroupUiAction.Save,
            upNavigation = upNavigation,
            handleTopBarNavClick = appState.handleTopBarNavClick,
            cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_group,
            uniqueConstraintFailedResId = R.string.group_unique_constraint_error,
            /*onActionBarChange = onActionBarChange,
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,*/
            onActionBarSubtitleChange = onActionBarSubtitleChange,
            onTopBarActionsChange = onTopBarActionsChange,
            //onFabChange = onFabChange
            innerPadding = innerPadding
        ) { _, _, _, handleSaveAction ->
            GroupView(
                sharedViewModel = appState.congregationSharedViewModel.value,
                handleSaveAction = handleSaveAction
            )
        }
    }
}
