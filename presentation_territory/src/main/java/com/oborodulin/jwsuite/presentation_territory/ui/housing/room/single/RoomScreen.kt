package com.oborodulin.jwsuite.presentation_territory.ui.housing.room.single

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import com.oborodulin.home.common.ui.components.screen.SaveDialogScreenComponent
import com.oborodulin.jwsuite.presentation.navigation.NavRoutes
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.RoomInput
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_territory.R
import timber.log.Timber

private const val TAG = "Territoring.RoomScreen"

@Composable
fun RoomScreen(
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    //territoryViewModel: TerritoryViewModelImpl = hiltViewModel(),
    viewModel: RoomViewModelImpl = hiltViewModel(),
    roomInput: RoomInput? = null,
    onActionBarChange: (@Composable (() -> Unit)?) -> Unit,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit,
    onFabChange: (@Composable () -> Unit) -> Unit
) {
    Timber.tag(TAG).d("RoomScreen(...) called: houseInput = %s", roomInput)
    val appState = LocalAppState.current
    val upNavigation: () -> Unit = { appState.mainNavigateUp(NavRoutes.Housing.route) }
    SaveDialogScreenComponent(
        viewModel = viewModel,
        inputId = roomInput?.roomId,
        loadUiAction = RoomUiAction.Load(roomInput?.roomId),
        saveUiAction = RoomUiAction.Save,
        upNavigation = upNavigation,
        handleTopBarNavClick = appState.handleTopBarNavClick,
        cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_room,
        uniqueConstraintFailedResId = R.string.room_unique_constraint_error,
        onActionBarChange = onActionBarChange,
        onActionBarSubtitleChange = onActionBarSubtitleChange,
        onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
        onTopBarActionsChange = onTopBarActionsChange,
        onFabChange = onFabChange
    ) { RoomView(sharedViewModel = appState.congregationViewModel.value) }
}