package com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single

import android.content.res.Configuration
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.oborodulin.home.common.ui.components.dialog.FullScreenDialog
import com.oborodulin.home.common.ui.components.field.ComboBoxComponent
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.home.common.util.OnListItemEvent
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list.RegionsListViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list.RegionsListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single.RegionViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single.RegionViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.list.RegionDistrictsListUiAction
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.list.RegionDistrictsListViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.list.RegionDistrictsListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import timber.log.Timber
import java.util.UUID

private const val TAG = "Geo.ui.RegionDistrictComboBox"

@Composable
fun RegionDistrictComboBox(
    modifier: Modifier = Modifier,
    regionId: UUID?,
    listViewModel: RegionDistrictsListViewModel,
    singleViewModel: RegionDistrictViewModel,
    regionsListViewModel: RegionsListViewModel,
    regionViewModel: RegionViewModel,
    inputWrapper: InputListItemWrapper,
    onValueChange: OnListItemEvent,
    onImeKeyAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("RegionComboBox(...) called")
    var isShowListDialog by remember { mutableStateOf(false) }
    val isShowNewRegionDistrictDialog by singleViewModel.showDialog.collectAsState()
    FullScreenDialog(
        isShow = isShowNewRegionDistrictDialog,
        viewModel = singleViewModel,
        loadUiAction = RegionDistrictUiAction.Load(),
        dialogView = {
            RegionDistrictView(
                singleViewModel,
                regionsListViewModel,
                regionViewModel
            )
        },
        onShowListDialog = { isShowListDialog = true }
    ) { singleViewModel.submitAction(RegionDistrictUiAction.Save) }

    ComboBoxComponent(
        modifier = modifier,
        listViewModel = listViewModel,
        loadListUiAction = RegionDistrictsListUiAction.Load(regionId),
        searchedItem = "",
        isShowListDialog = isShowListDialog,
        onShowListDialog = { isShowListDialog = true },
        onDismissListDialog = { isShowListDialog = false },
        onShowSingleDialog = { singleViewModel.onOpenDialogClicked() },
        labelResId = R.string.locality_region_district_hint,
        listTitleResId = R.string.dlg_title_select_region_district,
        leadingIcon = { Icon(painterResource(R.drawable.ic_district_36), null) },
        inputWrapper = inputWrapper,
        onValueChange = onValueChange,
        onImeKeyAction = onImeKeyAction
    )
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewRegionDistrictComboBox() {
    JWSuiteTheme {
        Surface {
            RegionDistrictComboBox(
                regionId = UUID.randomUUID(),
                listViewModel = RegionDistrictsListViewModelImpl.previewModel(LocalContext.current),
                singleViewModel = RegionDistrictViewModelImpl.previewModel(LocalContext.current),
                regionsListViewModel = RegionsListViewModelImpl.previewModel(LocalContext.current),
                regionViewModel = RegionViewModelImpl.previewModel(LocalContext.current),
                inputWrapper = InputListItemWrapper(),
                onValueChange = {},
                onImeKeyAction = {}
            )
        }
    }
}
