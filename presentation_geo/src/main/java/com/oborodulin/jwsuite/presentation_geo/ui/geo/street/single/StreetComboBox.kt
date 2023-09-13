package com.oborodulin.jwsuite.presentation_geo.ui.geo.street.single

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
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_geo.R
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.list.StreetsListUiAction
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.list.StreetsListViewModelImpl
import com.oborodulin.jwsuite.presentation_geo.ui.model.StreetsListItem
import com.oborodulin.jwsuite.presentation_geo.ui.model.toStreetsListItem
import timber.log.Timber
import java.util.UUID

private const val TAG = "Geo.StreetComboBox"

@Composable
fun StreetComboBox(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    listViewModel: StreetsListViewModelImpl = hiltViewModel(),
    singleViewModel: StreetViewModelImpl = hiltViewModel(),
    localityId: UUID? = null,
    localityDistrictId: UUID? = null,
    microdistrictId: UUID? = null,
    loadListUiAction: StreetsListUiAction = StreetsListUiAction.Load(
        localityId = localityId,
        localityDistrictId = localityDistrictId,
        microdistrictId = microdistrictId
    ),
    inputWrapper: InputListItemWrapper<StreetsListItem>,
    onValueChange: (StreetsListItem) -> Unit = {},
    onImeKeyAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("StreetComboBox(...) called")
    var isShowListDialog by rememberSaveable { mutableStateOf(false) }
    val onShowListDialog = { isShowListDialog = true }
    val onDismissListDialog = { isShowListDialog = false }
    val isShowNewSingleDialog by singleViewModel.showDialog.collectAsStateWithLifecycle()
    FullScreenDialog(
        isShow = isShowNewSingleDialog,
        viewModel = singleViewModel,
        loadUiAction = StreetUiAction.Load(),
        confirmUiAction = StreetUiAction.Save,
        dialogView = { StreetView() },
        onValueChange = { onValueChange(it.toStreetsListItem()) }
    )
    ComboBoxComponent(
        modifier = modifier,
        enabled = enabled,
        listViewModel = listViewModel,
        loadListUiAction = loadListUiAction,
        isShowListDialog = isShowListDialog,
        onShowListDialog = onShowListDialog,
        onDismissListDialog = onDismissListDialog,
        onShowSingleDialog = { singleViewModel.onOpenDialogClicked() },
        labelResId = R.string.street_hint,
        listTitleResId = R.string.dlg_title_select_street,
        leadingPainterResId = R.drawable.ic_street_sign_36,
        inputWrapper = inputWrapper,
        onValueChange = { onValueChange(it.toStreetsListItem()) },
        onImeKeyAction = onImeKeyAction
    )
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewStreetComboBox() {
    JWSuiteTheme {
        Surface {
            /*StreetComboBox(
                listViewModel = StreetsListViewModelImpl.previewModel(LocalContext.current),
                singleViewModel = StreetViewModelImpl.previewModel(LocalContext.current),
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
