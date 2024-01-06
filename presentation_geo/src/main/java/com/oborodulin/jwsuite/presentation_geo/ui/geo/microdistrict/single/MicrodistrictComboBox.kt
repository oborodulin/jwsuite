package com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.single

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
import com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.list.MicrodistrictsListUiAction
import com.oborodulin.jwsuite.presentation_geo.ui.geo.microdistrict.list.MicrodistrictsListViewModelImpl
import timber.log.Timber
import java.util.UUID

private const val TAG = "Geo.MicrodistrictComboBox"

@Composable
fun MicrodistrictComboBox(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    localityId: UUID?,
    localityDistrictId: UUID?,
    listViewModel: MicrodistrictsListViewModelImpl = hiltViewModel(),
    singleViewModel: MicrodistrictViewModelImpl = hiltViewModel(),
    inputWrapper: InputListItemWrapper<ListItemModel>,
    onValueChange: OnListItemEvent,
    onImeKeyAction: OnImeKeyAction
) {
    Timber.tag(TAG).d(
        "MicrodistrictComboBox(...) called: localityId = %s; localityDistrictId = %s",
        localityId,
        localityDistrictId
    )
    var isShowListDialog by rememberSaveable { mutableStateOf(false) }
    val onShowListDialog = { isShowListDialog = true }
    val onDismissListDialog = { isShowListDialog = false }
    val isShowNewSingleDialog by singleViewModel.showDialog.collectAsStateWithLifecycle()
    FullScreenDialog(
        isShow = isShowNewSingleDialog,
        viewModel = singleViewModel,
        loadUiAction = MicrodistrictUiAction.Load(),
        confirmUiAction = MicrodistrictUiAction.Save,
        dialogView = { _, handleConfirmAction -> MicrodistrictView(handleSaveAction = handleConfirmAction) },
        onValueChange = onValueChange,
    )
    ComboBoxComponent(
        modifier = modifier,
        enabled = enabled,
        listViewModel = listViewModel,
        loadListUiAction = MicrodistrictsListUiAction.Load(localityId, localityDistrictId),
        isShowListDialog = isShowListDialog,
        onShowListDialog = onShowListDialog,
        onDismissListDialog = onDismissListDialog,
        onShowSingleDialog = { singleViewModel.onOpenDialogClicked() },
        labelResId = R.string.microdistrict_hint,
        listTitleResId = R.string.dlg_title_select_microdistrict,
        leadingPainterResId = R.drawable.ic_microdistrict_36,
        inputWrapper = inputWrapper,
        onValueChange = onValueChange,
        onImeKeyAction = onImeKeyAction
    )
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewMicrodistrictComboBox() {
    JWSuiteTheme {
        Surface {
            /*MicrodistrictComboBox(
                listViewModel = MicrodistrictsListViewModelImpl.previewModel(LocalContext.current),
                singleViewModel = MicrodistrictViewModelImpl.previewModel(LocalContext.current),
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
