package com.oborodulin.jwsuite.presentation_territory.ui.territoring.room.territory

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.ui.state.SharedViewModeled
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.home.common.util.OnListItemEvent
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_territory.R
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.list.HousesListUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.list.HousesListViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.single.HouseUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.single.HouseView
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.house.single.HouseViewModelImpl
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryUiAction
import com.oborodulin.jwsuite.presentation_territory.ui.territoring.territory.single.TerritoryViewModelImpl
import timber.log.Timber
import java.util.UUID

private const val TAG = "Territoring.TerritoryHouseComboBox"

@Composable
fun TerritoryHouseComboBox(
    modifier: Modifier = Modifier,
    territoryId: UUID,
    sharedViewModel: SharedViewModeled<ListItemModel?>?,
    territoryViewModel: TerritoryViewModelImpl = hiltViewModel(),
    listViewModel: HousesListViewModelImpl = hiltViewModel(),
    singleViewModel: HouseViewModelImpl = hiltViewModel(),
    inputWrapper: InputListItemWrapper<ListItemModel>,
    onValueChange: OnListItemEvent,
    onImeKeyAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("TerritoryHouseComboBox(...) called: territoryId = %s", territoryId)
    LaunchedEffect(territoryId) {
        Timber.tag(TAG)
            .d("TerritoryHouseComboBox -> LaunchedEffect(): territoryId = %s", territoryId)
        territoryViewModel.submitAction(TerritoryUiAction.Load(territoryId))
    }
    var isShowListDialog by rememberSaveable { mutableStateOf(false) }
    val onShowListDialog = { isShowListDialog = true }
    val onDismissListDialog = { isShowListDialog = false }
    val isShowNewSingleDialog by singleViewModel.showDialog.collectAsStateWithLifecycle()
    FullScreenDialog(
        isShow = isShowNewSingleDialog,
        viewModel = singleViewModel,
        loadUiAction = HouseUiAction.Load(),
        confirmUiAction = HouseUiAction.Save,
        dialogView = {
            territoryViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
                Timber.tag(TAG).d("Collect ui state flow: %s", state)
                CommonScreen(state = state) {
                    HouseView(
                        territoryUiModel = it,
                        sharedViewModel = sharedViewModel,
                        territoryViewModel = territoryViewModel
                    )
                }
            }
        },
        onValueChange = onValueChange,
    )
    ComboBoxComponent(
        modifier = modifier,
        listViewModel = listViewModel,
        loadListUiAction = HousesListUiAction.Load(territoryId = territoryId),
        isShowListDialog = isShowListDialog,
        onShowListDialog = onShowListDialog,
        onDismissListDialog = onDismissListDialog,
        onShowSingleDialog = { singleViewModel.onOpenDialogClicked() },
        labelResId = R.string.house_hint,
        listTitleResId = R.string.dlg_title_select_house,
        leadingImageVector = Icons.Outlined.Home,
        inputWrapper = inputWrapper,
        onValueChange = onValueChange,
        onImeKeyAction = onImeKeyAction
    )
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewTerritoryHouseComboBox() {
    JWSuiteTheme {
        Surface {
            /*TerritoryHouseComboBox(
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
