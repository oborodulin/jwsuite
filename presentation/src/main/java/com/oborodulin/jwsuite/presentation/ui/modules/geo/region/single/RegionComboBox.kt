package com.oborodulin.jwsuite.presentation.ui.modules.geo.region.single

import android.content.res.Configuration
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list.RegionsListUiAction
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list.RegionsListViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.geo.region.list.RegionsListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import timber.log.Timber

private const val TAG = "Geo.ui.RegionComboBox"

@Composable
fun RegionComboBox(
    modifier: Modifier = Modifier,
    listViewModel: RegionsListViewModel,
    singleViewModel: RegionViewModel,
    inputWrapper: InputListItemWrapper,
    onValueChange: OnListItemEvent,
    onImeKeyAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("RegionComboBox(...) called")
    val isShowNewRegionDialog by singleViewModel.showDialog.collectAsState()
    FullScreenDialog(
        isShow = isShowNewRegionDialog,
        viewModel = singleViewModel,
        dialogView = { RegionView(singleViewModel) }
    ) { singleViewModel.submitAction(RegionUiAction.Save) }

    ComboBoxComponent(
        modifier = modifier,
        listViewModel = listViewModel,
        loadListUiAction = RegionsListUiAction.Load,
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
                singleViewModel = RegionViewModelImpl.previewModel(LocalContext.current),
                inputWrapper = InputListItemWrapper(),
                onValueChange = {},
                onImeKeyAction = {}
            )
        }
    }
}
