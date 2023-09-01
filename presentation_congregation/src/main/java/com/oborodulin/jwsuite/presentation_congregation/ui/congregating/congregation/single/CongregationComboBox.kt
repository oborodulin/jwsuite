package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.single

import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.dialog.FullScreenDialog
import com.oborodulin.home.common.ui.components.field.ComboBoxComponent
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.home.common.util.OnListItemEvent
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_congregation.R
import com.oborodulin.jwsuite.presentation_congregation.ui.FavoriteCongregationViewModel
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.list.CongregationsListUiAction
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.list.CongregationsListViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem
import timber.log.Timber

private const val TAG = "Congregating.CongregationComboBox"

@Composable
fun CongregationComboBox(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    sharedViewModel: FavoriteCongregationViewModel<CongregationsListItem?>,
    listViewModel: CongregationsListViewModelImpl = hiltViewModel(),
    singleViewModel: CongregationViewModelImpl = hiltViewModel(),
    inputWrapper: InputListItemWrapper<ListItemModel>,
    onValueChange: OnListItemEvent,
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
        dialogView = { CongregationView() },
        onValueChange = onValueChange,
        //onShowListDialog = onShowListDialog
    )
    val currentCongregation = sharedViewModel.sharedFlow.collectAsStateWithLifecycle().value
    Timber.tag(TAG).d("currentCongregation = %s", currentCongregation)
    currentCongregation?.let { onValueChange(ListItemModel(it.id, it.headline)) }
    ComboBoxComponent(
        modifier = modifier,
        enabled = enabled,
        listViewModel = listViewModel,
        loadListUiAction = CongregationsListUiAction.Load,
        isShowListDialog = isShowListDialog,
        onShowListDialog = onShowListDialog,
        onDismissListDialog = onDismissListDialog,
        onShowSingleDialog = { singleViewModel.onOpenDialogClicked() },
        labelResId = R.string.congregation_hint,
        listTitleResId = R.string.dlg_title_select_congregation,
        leadingPainterResId = R.drawable.ic_congregation_36,
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
/*            CongregationComboBox(
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
            )*/
        }
    }
}
