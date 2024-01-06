package com.oborodulin.jwsuite.presentation_territory.ui.housing.room.single

import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.dialog.FullScreenDialog
import com.oborodulin.home.common.ui.components.field.ComboBoxComponent
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.ui.state.SharedViewModeled
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.home.common.util.OnListItemEvent
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.housing.room.list.RoomsListUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.housing.room.list.RoomsListViewModelImpl
import timber.log.Timber
import java.util.UUID

private const val TAG = "Housing.RoomComboBox"

@Composable
fun RoomComboBox(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    houseId: UUID? = null,
    sharedViewModel: SharedViewModeled<ListItemModel?>?,
    listViewModel: RoomsListViewModelImpl = hiltViewModel(),
    singleViewModel: RoomViewModelImpl = hiltViewModel(),
    inputWrapper: InputListItemWrapper<ListItemModel>,
    onValueChange: OnListItemEvent,
    onImeKeyAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("RoomComboBox(...) called: houseId = %s", houseId)
    var isShowListDialog by rememberSaveable { mutableStateOf(false) }
    val onShowListDialog = { isShowListDialog = true }
    val onDismissListDialog = { isShowListDialog = false }
    val isShowNewSingleDialog by singleViewModel.showDialog.collectAsStateWithLifecycle()
    FullScreenDialog(
        isShow = isShowNewSingleDialog,
        viewModel = singleViewModel,
        loadUiAction = RoomUiAction.Load(),
        confirmUiAction = RoomUiAction.Save,
        dialogView = { _, handleConfirmAction ->
            RoomView(
                sharedViewModel = sharedViewModel,
                handleSaveAction = handleConfirmAction
            )
        },
        onValueChange = onValueChange
    )
    ComboBoxComponent(
        modifier = modifier,
        enabled = enabled,
        listViewModel = listViewModel,
        loadListUiAction = RoomsListUiAction.Load(houseId = houseId),
        isShowListDialog = isShowListDialog,
        onShowListDialog = onShowListDialog,
        onDismissListDialog = onDismissListDialog,
        onShowSingleDialog = { singleViewModel.onOpenDialogClicked() },
        labelResId = R.string.room_hint,
        listTitleResId = R.string.dlg_title_select_room,
        leadingPainterResId = R.drawable.ic_room_36,
        inputWrapper = inputWrapper,
        onValueChange = onValueChange,
        onImeKeyAction = onImeKeyAction
    )
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewRoomComboBox() {
    JWSuiteTheme {
        Surface {
            /*RoomComboBox(
                listViewModel = RoomsListViewModelImpl.previewModel(LocalContext.current),
                singleViewModel = RoomViewModelImpl.previewModel(LocalContext.current),
                regionsListViewModel = RegionsListViewModelImpl.previewModel(LocalContext.current),
                regionViewModel = RegionViewModelImpl.previewModel(LocalContext.current),
                regionDistrictsListViewModel = RegionDistrictsListViewModelImpl.previewModel(
                    LocalContext.current
                ),
                regionDistrictViewModel = RegionDistrictViewModelImpl.previewModel(LocalContext.current),
                inputWrapper = InputListItemWrapper(),
                onValueChange = {},
                onImeKeyAction = {}
            )*/
        }
    }
}
