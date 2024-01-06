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
import com.oborodulin.home.common.ui.components.bar.BarComboBoxComponent
import com.oborodulin.home.common.ui.components.dialog.FullScreenDialog
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.home.common.util.OnListItemEvent
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_geo.R
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.list.StreetsListUiAction
import com.oborodulin.jwsuite.presentation_geo.ui.geo.street.list.StreetsListViewModelImpl
import timber.log.Timber
import java.util.UUID

private const val TAG = "Geo.BarStreetComboBox"

@Composable
fun BarStreetComboBox(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    listViewModel: StreetsListViewModelImpl = hiltViewModel(),
    singleViewModel: StreetViewModelImpl = hiltViewModel(),
    localityId: UUID? = null,
    localityDistrictId: UUID? = null,
    microdistrictId: UUID? = null,
    inputWrapper: InputListItemWrapper<ListItemModel>,
    onValueChange: OnListItemEvent,
    onImeKeyAction: OnImeKeyAction = {}
) {
    Timber.tag(TAG).d(
        "BarStreetComboBox(...) called: localityId = %s; localityDistrictId = %s; microdistrictId = %s",
        localityId,
        localityDistrictId,
        microdistrictId
    )
    var isShowListDialog by rememberSaveable { mutableStateOf(false) }
    val onShowListDialog = { isShowListDialog = true }
    val onDismissListDialog = { isShowListDialog = false }
    val isShowNewSingleDialog by singleViewModel.showDialog.collectAsStateWithLifecycle()
    FullScreenDialog(
        isShow = isShowNewSingleDialog,
        viewModel = singleViewModel,
        loadUiAction = StreetUiAction.Load(),
        confirmUiAction = StreetUiAction.Save,
        dialogView = { _, handleConfirmAction -> StreetView(handleSaveAction = handleConfirmAction) },
        onValueChange = onValueChange
    )
    BarComboBoxComponent(
        modifier = modifier,
        enabled = enabled,
        listViewModel = listViewModel,
        loadListUiAction = StreetsListUiAction.Load(
            localityId = localityId,
            localityDistrictId = localityDistrictId,
            microdistrictId = microdistrictId
        ),
        isShowListDialog = isShowListDialog,
        onShowListDialog = onShowListDialog,
        onDismissListDialog = onDismissListDialog,
        onShowSingleDialog = { singleViewModel.onOpenDialogClicked() },
        placeholderResId = R.string.street_placeholder,
        listTitleResId = R.string.dlg_title_select_street,
        leadingPainterResId = com.oborodulin.jwsuite.presentation.R.drawable.ic_street_sign_24,
        inputWrapper = inputWrapper,
        onValueChange = onValueChange,
        onImeKeyAction = onImeKeyAction
    )
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewBarStreetComboBox() {
    JWSuiteTheme {
        Surface {
            /*BarStreetComboBox(
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
