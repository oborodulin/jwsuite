package com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.single

import android.content.res.Configuration
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.dialog.FullScreenDialog
import com.oborodulin.home.common.ui.components.field.ComboBoxComponent
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.home.common.util.OnListItemEvent
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.list.CongregationsListUiAction
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.list.CongregationsListViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.list.CongregationsListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.list.LocalitiesListViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.list.LocalitiesListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.single.LocalityViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.locality.single.LocalityViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list.RegionsListViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list.RegionsListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single.RegionViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single.RegionViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.list.RegionDistrictsListViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.list.RegionDistrictsListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single.RegionDistrictViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.regiondistrict.single.RegionDistrictViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import timber.log.Timber

private const val TAG = "Congregating.CongregationComboBox"

@Composable
fun CongregationComboBox(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    listViewModel: CongregationsListViewModel,
    singleViewModel: CongregationViewModel,
    localitiesListViewModel: LocalitiesListViewModel,
    localityViewModel: LocalityViewModel,
    regionsListViewModel: RegionsListViewModel,
    regionViewModel: RegionViewModel,
    regionDistrictsListViewModel: RegionDistrictsListViewModel,
    regionDistrictViewModel: RegionDistrictViewModel,
    inputWrapper: InputListItemWrapper,
    onValueChange: OnListItemEvent = {},
    onImeKeyAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("CongregationComboBox(...) called")
    var isShowListDialog by remember { mutableStateOf(false) }
    val onShowListDialog = { isShowListDialog = true }
    val onDismissListDialog = { isShowListDialog = false }
    val isShowNewSingleDialog by singleViewModel.showDialog.collectAsStateWithLifecycle()
    FullScreenDialog(
        isShow = isShowNewSingleDialog,
        viewModel = singleViewModel,
        loadUiAction = CongregationUiAction.Load(),
        confirmUiAction = CongregationUiAction.Save,
        dialogView = {
            CongregationView(
                singleViewModel,
                localitiesListViewModel,
                localityViewModel,
                regionsListViewModel,
                regionViewModel,
                regionDistrictsListViewModel,
                regionDistrictViewModel
            )
        },
        onValueChange = onValueChange,
        //onShowListDialog = onShowListDialog
    )
    ComboBoxComponent(
        modifier = modifier,
        enabled = enabled,
        listViewModel = listViewModel,
        loadListUiAction = CongregationsListUiAction.Load,
        isShowListDialog = isShowListDialog,
        onShowListDialog = onShowListDialog,
        onDismissListDialog = onDismissListDialog,
        onShowSingleDialog = { singleViewModel.onOpenDialogClicked() },
        labelResId = R.string.member_group_num_hint,
        listTitleResId = R.string.dlg_title_select_group,
        leadingIcon = { Icon(painterResource(R.drawable.ic_congregation_36), null) },
        inputWrapper = inputWrapper,
        onValueChange = onValueChange,
        onImeKeyAction = onImeKeyAction
    )
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewCongregationComboBox() {
    JWSuiteTheme {
        Surface {
            CongregationComboBox(
                listViewModel = CongregationsListViewModelImpl.previewModel(LocalContext.current),
                singleViewModel = CongregationViewModelImpl.previewModel(LocalContext.current),
                localitiesListViewModel = LocalitiesListViewModelImpl.previewModel(LocalContext.current),
                localityViewModel = LocalityViewModelImpl.previewModel(LocalContext.current),
                regionsListViewModel = RegionsListViewModelImpl.previewModel(LocalContext.current),
                regionViewModel = RegionViewModelImpl.previewModel(LocalContext.current),
                regionDistrictsListViewModel = RegionDistrictsListViewModelImpl.previewModel(
                    LocalContext.current
                ),
                regionDistrictViewModel = RegionDistrictViewModelImpl.previewModel(LocalContext.current),
                inputWrapper = InputListItemWrapper(),
                onValueChange = {},
                onImeKeyAction = {}
            )
        }
    }
}
