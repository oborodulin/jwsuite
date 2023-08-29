package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.single

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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.dialog.FullScreenDialog
import com.oborodulin.home.common.ui.components.field.ComboBoxComponent
import com.oborodulin.home.common.ui.components.field.util.InputListItemWrapper
import com.oborodulin.home.common.ui.model.ListItemModel
import com.oborodulin.home.common.util.OnImeKeyAction
import com.oborodulin.home.common.util.OnListItemEvent
import com.oborodulin.jwsuite.presentation.R
import com.oborodulin.jwsuite.presentation.ui.modules.FavoriteCongregationViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.FavoriteCongregationViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationsListItem
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.list.TerritoriesListUiAction
import com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.list.TerritoriesListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import timber.log.Timber

private const val TAG = "Territoring.TerritoryComboBox"

@Composable
fun TerritoryComboBox(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    sharedViewModel: FavoriteCongregationViewModel<CongregationsListItem?>?,
    listViewModel: TerritoriesListViewModelImpl = hiltViewModel(),
    singleViewModel: TerritoryViewModelImpl = hiltViewModel(),
    inputWrapper: InputListItemWrapper<ListItemModel>,
    onValueChange: OnListItemEvent = {},
    onImeKeyAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("TerritoryComboBox(...) called")
    var isShowListDialog by remember { mutableStateOf(false) }
    val onShowListDialog = { isShowListDialog = true }
    val onDismissListDialog = { isShowListDialog = false }
    val isShowNewSingleDialog by singleViewModel.showDialog.collectAsStateWithLifecycle()
    FullScreenDialog(
        isShow = isShowNewSingleDialog,
        viewModel = singleViewModel,
        loadUiAction = TerritoryUiAction.Load(),
        confirmUiAction = TerritoryUiAction.Save,
        dialogView = { TerritoryView(sharedViewModel) },
        onValueChange = onValueChange,
        //onShowListDialog = onShowListDialog
    )
    val currentCongregation = sharedViewModel?.sharedFlow?.collectAsStateWithLifecycle()?.value
    Timber.tag(TAG).d("currentCongregation = %s", currentCongregation)
    ComboBoxComponent(
        modifier = modifier,
        enabled = enabled,
        listViewModel = listViewModel,
        loadListUiAction = TerritoriesListUiAction.Load(currentCongregation?.id),
        isShowListDialog = isShowListDialog,
        onShowListDialog = onShowListDialog,
        onDismissListDialog = onDismissListDialog,
        onShowSingleDialog = { singleViewModel.onOpenDialogClicked() },
        labelResId = R.string.territory_hint,
        listTitleResId = R.string.dlg_title_select_territory,
        leadingIcon = { Icon(painterResource(R.drawable.ic_map_marker_36), null) },
        inputWrapper = inputWrapper,
        onValueChange = onValueChange,
        onImeKeyAction = onImeKeyAction
    )
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewTerritoryComboBox() {
    val ctx = LocalContext.current
    JWSuiteTheme {
        Surface {
            TerritoryComboBox(
                sharedViewModel = FavoriteCongregationViewModelImpl.previewModel,
                inputWrapper = InputListItemWrapper(),
                onValueChange = {},
                onImeKeyAction = {}
            )
        }
    }
}
