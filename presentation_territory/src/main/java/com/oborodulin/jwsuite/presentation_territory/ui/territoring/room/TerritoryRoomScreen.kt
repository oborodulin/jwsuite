package com.oborodulin.jwsuite.presentation_territory.ui.territoring.room

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import com.oborodulin.home.common.ui.components.screen.SaveDialogScreenComponent
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.TerritoryRoomInput
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModel
import timber.log.Timber

private const val TAG = "Territoring.TerritoryRoomScreen"

@Composable
fun TerritoryRoomScreen(
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    territoryViewModel: TerritoryViewModel,
    territoryRoomViewModel: TerritoryRoomViewModelImpl = hiltViewModel(),
    territoryRoomInput: TerritoryRoomInput,
    onActionBarSubtitleChange: (String) -> Unit,
    onTopBarNavImageVectorChange: (ImageVector?) -> Unit,
    onTopBarActionsChange: (Boolean, (@Composable RowScope.() -> Unit)) -> Unit
) {
    Timber.tag(TAG)
        .d("TerritoryRoomScreen(...) called: territoryRoomInput = %s", territoryRoomInput)
    val appState = LocalAppState.current
    val upNavigation: () -> Unit = { appState.mainNavigateUp() }
    SaveDialogScreenComponent(
        viewModel = territoryRoomViewModel,
        inputId = territoryRoomInput.territoryId,
        loadUiAction = TerritoryRoomUiAction.Load(territoryRoomInput.territoryId),
        saveUiAction = TerritoryRoomUiAction.Save,
        upNavigation = upNavigation,
        handleTopBarNavClick = appState.handleTopBarNavClick,
        cancelChangesConfirmResId = R.string.dlg_confirm_cancel_changes_territory_room,
        onActionBarSubtitleChange = onActionBarSubtitleChange,
        onTopBarNavImageVectorChange = onTopBarNavImageVectorChange,
        onTopBarActionsChange = onTopBarActionsChange
    ) {
        TerritoryRoomView(
            territoryRoomsUiModel = it,
            sharedViewModel = appState.sharedViewModel.value,
            territoryViewModel = territoryViewModel,
            territoryRoomViewModel = territoryRoomViewModel
        )
    }
}