package com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.single

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
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.home.common.util.OnListItemEvent
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_geo.R
import com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.list.LocalityDistrictsListUiAction
import com.oborodulin.jwsuite.presentation_geo.ui.geo.localitydistrict.list.LocalityDistrictsListViewModelImpl
import timber.log.Timber
import java.util.UUID

private const val TAG = "Geo.LocalityDistrictComboBox"

@Composable
fun LocalityDistrictComboBox(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    localityId: UUID?,
    listViewModel: LocalityDistrictsListViewModelImpl = hiltViewModel(),
    singleViewModel: LocalityDistrictViewModelImpl = hiltViewModel(),
    inputWrapper: InputListItemWrapper<ListItemModel>,
    onValueChange: OnListItemEvent,
    onImeKeyAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("LocalityDistrictComboBox(...) called: localityId = %s", localityId)
    var isShowListDialog by rememberSaveable { mutableStateOf(false) }
    val onShowListDialog = { isShowListDialog = true }
    val onDismissListDialog = { isShowListDialog = false }
    val isShowNewSingleDialog by singleViewModel.showDialog.collectAsStateWithLifecycle()
    FullScreenDialog(
        isShow = isShowNewSingleDialog,
        viewModel = singleViewModel,
        loadUiAction = LocalityDistrictUiAction.Load(),
        confirmUiAction = LocalityDistrictUiAction.Save,
        dialogView = { _, handleConfirmAction -> LocalityDistrictView(handleSaveAction = handleConfirmAction) },
        onValueChange = onValueChange,
        //onShowListDialog = onShowListDialog
    )

    ComboBoxComponent(
        modifier = modifier,
        enabled = enabled,
        listViewModel = listViewModel,
        loadListUiAction = LocalityDistrictsListUiAction.Load(localityId),
        isShowListDialog = isShowListDialog,
        onShowListDialog = onShowListDialog,
        onDismissListDialog = onDismissListDialog,
        onShowSingleDialog = { singleViewModel.onOpenDialogClicked() },
        labelResId = R.string.locality_district_hint,
        listTitleResId = R.string.dlg_title_select_locality_district,
        leadingPainterResId = R.drawable.ic_locality_district_36,
        inputWrapper = inputWrapper,
        onValueChange = onValueChange,
        onImeKeyAction = onImeKeyAction
    )
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewLocalityDistrictComboBox() {
    JWSuiteTheme {
        Surface {
            /*LocalityDistrictComboBox(
                localityId = UUID.randomUUID(),
                listViewModel = LocalityDistrictsListViewModelImpl.previewModel(LocalContext.current),
                singleViewModel = LocalityDistrictViewModelImpl.previewModel(LocalContext.current),
                localitysListViewModel = LocalitysListViewModelImpl.previewModel(LocalContext.current),
                localityViewModel = LocalityViewModelImpl.previewModel(LocalContext.current),
                inputWrapper = InputListItemWrapper(),
                onValueChange = {},
                onImeKeyAction = {}
            )*/
        }
    }
}
