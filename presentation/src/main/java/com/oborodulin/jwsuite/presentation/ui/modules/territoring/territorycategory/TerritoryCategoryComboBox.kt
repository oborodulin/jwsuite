package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territorycategory

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
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list.RegionsListUiAction
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list.RegionsListViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list.RegionsListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import timber.log.Timber

private const val TAG = "Territoring.RegionComboBox"

@Composable
fun RegionComboBox(
    modifier: Modifier = Modifier,
    listViewModel: RegionsListViewModel,
    singleViewModel: TerritoryCategoryViewModel,
    inputWrapper: InputListItemWrapper,
    onValueChange: OnListItemEvent,
    onImeKeyAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("RegionComboBox(...) called")
    var isShowListDialog by remember { mutableStateOf(false) }
    val onShowListDialog = { isShowListDialog = true }
    val onDismissListDialog = { isShowListDialog = false }
    val isShowNewSingleDialog by singleViewModel.showDialog.collectAsStateWithLifecycle()
    FullScreenDialog(
        isShow = isShowNewSingleDialog,
        viewModel = singleViewModel,
        loadUiAction = TerritoryCategoryUiAction.Load(),
        confirmUiAction = TerritoryCategoryUiAction.Save,
        dialogView = { RegionView(singleViewModel) },
        onValueChange = onValueChange,
        //onShowListDialog = onShowListDialog
    )
    ComboBoxComponent(
        modifier = modifier,
        listViewModel = listViewModel,
        loadListUiAction = RegionsListUiAction.Load,
        searchedItem = "",
        isShowListDialog = isShowListDialog,
        onShowListDialog = onShowListDialog,
        onDismissListDialog = onDismissListDialog,
        onShowSingleDialog = { singleViewModel.onOpenDialogClicked() },
        labelResId = R.string.locality_region_hint,
        listTitleResId = R.string.dlg_title_select_region,
        leadingIcon = { Icon(painterResource(R.drawable.ic_region_36), null) },
        inputWrapper = inputWrapper,
        onValueChange = onValueChange,
        onImeKeyAction = onImeKeyAction
    )
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewRegionComboBox() {
    JWSuiteTheme {
        Surface {
            RegionComboBox(
                listViewModel = RegionsListViewModelImpl.previewModel(LocalContext.current),
                singleViewModel = TerritoryCategoryViewModelImpl.previewModel(LocalContext.current),
                inputWrapper = InputListItemWrapper(),
                onValueChange = {},
                onImeKeyAction = {}
            )
        }
    }
}
