package com.oborodulin.jwsuite.presentation_territory.ui.housing.house.single

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
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
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.housing.house.list.HousesListUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.housing.house.list.HousesListViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.model.HousesListItem
import com.oborodulin.jwsuite.presentation_territory.ui.model.toHousesListItem
import timber.log.Timber
import java.util.UUID

private const val TAG = "Housing.HouseComboBox"

@Composable
fun HouseComboBox(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    streetId: UUID? = null,
    sharedViewModel: SharedViewModeled<ListItemModel?>?,
    listViewModel: HousesListViewModelImpl = hiltViewModel(),
    singleViewModel: HouseViewModelImpl = hiltViewModel(),
    inputWrapper: InputListItemWrapper<HousesListItem>,
    onValueChange: (HousesListItem) -> Unit = {},
    onImeKeyAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("HouseComboBox(...) called: streetId = %s", streetId)
    var isShowListDialog by rememberSaveable { mutableStateOf(false) }
    val onShowListDialog = { isShowListDialog = true }
    val onDismissListDialog = { isShowListDialog = false }
    val isShowNewSingleDialog by singleViewModel.showDialog.collectAsStateWithLifecycle()
    FullScreenDialog(
        isShow = isShowNewSingleDialog,
        viewModel = singleViewModel,
        loadUiAction = HouseUiAction.Load(),
        confirmUiAction = HouseUiAction.Save,
        dialogView = { _, handleConfirmAction ->
            HouseView(
                sharedViewModel = sharedViewModel,
                handleSaveAction = handleConfirmAction
            )
        },
        onValueChange = { onValueChange(it.toHousesListItem()) }
    )
    ComboBoxComponent(
        modifier = modifier,
        enabled = enabled,
        listViewModel = listViewModel,
        loadListUiAction = HousesListUiAction.Load(streetId = streetId),
        isShowListDialog = isShowListDialog,
        onShowListDialog = onShowListDialog,
        onDismissListDialog = onDismissListDialog,
        onShowSingleDialog = { singleViewModel.onOpenDialogClicked() },
        labelResId = R.string.house_hint,
        listTitleResId = R.string.dlg_title_select_house,
        leadingImageVector = Icons.Outlined.Home,
        inputWrapper = inputWrapper,
        onValueChange = { onValueChange(it.toHousesListItem()) },
        onImeKeyAction = onImeKeyAction
    )
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewHouseComboBox() {
    JWSuiteTheme {
        Surface {
            /*HouseComboBox(
                listViewModel = HousesListViewModelImpl.previewModel(LocalContext.current),
                singleViewModel = HouseViewModelImpl.previewModel(LocalContext.current),
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
