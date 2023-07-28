package com.oborodulin.jwsuite.presentation.ui.modules.congregating.group.single

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
import com.oborodulin.jwsuite.presentation.ui.modules.FavoriteCongregationViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.group.list.GroupsListUiAction
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.group.list.GroupsListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import timber.log.Timber

private const val TAG = "Congregating.GroupComboBox"

@Composable
fun GroupComboBox(
    modifier: Modifier = Modifier,
    sharedViewModel: FavoriteCongregationViewModelImpl = hiltViewModel(),
    listViewModel: GroupsListViewModelImpl = hiltViewModel(),
    singleViewModel: GroupViewModelImpl = hiltViewModel(),
    inputWrapper: InputListItemWrapper<ListItemModel>,
    onValueChange: OnListItemEvent,
    onImeKeyAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("GroupComboBox(...) called")
    var isShowListDialog by remember { mutableStateOf(false) }
    val onShowListDialog = { isShowListDialog = true }
    val onDismissListDialog = { isShowListDialog = false }
    val isShowNewSingleDialog by singleViewModel.showDialog.collectAsStateWithLifecycle()
    FullScreenDialog(
        isShow = isShowNewSingleDialog,
        viewModel = singleViewModel,
        loadUiAction = GroupUiAction.Load(),
        confirmUiAction = GroupUiAction.Save,
        dialogView = { GroupView(sharedViewModel, singleViewModel) },
        onValueChange = onValueChange,
        //onShowListDialog = onShowListDialog
    )
    val currentCongregation by sharedViewModel.sharedFlow.collectAsStateWithLifecycle(null)
    ComboBoxComponent(
        modifier = modifier,
        listViewModel = listViewModel,
        loadListUiAction = GroupsListUiAction.Load(currentCongregation?.id),
        searchedItem = "",
        isShowListDialog = isShowListDialog,
        onShowListDialog = onShowListDialog,
        onDismissListDialog = onDismissListDialog,
        onShowSingleDialog = { singleViewModel.onOpenDialogClicked() },
        labelResId = R.string.member_group_num_hint,
        listTitleResId = R.string.dlg_title_select_group,
        leadingIcon = {
            Icon(
                painterResource(com.oborodulin.home.common.R.drawable.ic_123_36),
                null
            )
        },
        inputWrapper = inputWrapper,
        onValueChange = onValueChange,
        onImeKeyAction = onImeKeyAction
    )
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewGroupComboBox() {
    val ctx = LocalContext.current
    JWSuiteTheme {
        Surface {
/*            GroupComboBox(
                sharedViewModel = FavoriteCongregationViewModelImpl.previewModel,
                listViewModel = GroupsListViewModelImpl.previewModel(ctx),
                singleViewModel = GroupViewModelImpl.previewModel(ctx),
                congregationsListViewModel = CongregationsListViewModelImpl.previewModel(ctx),
                congregationViewModel = CongregationViewModelImpl.previewModel(ctx),
                localitiesListViewModel = LocalitiesListViewModelImpl.previewModel(ctx),
                localityViewModel = LocalityViewModelImpl.previewModel(ctx),
                regionsListViewModel = RegionsListViewModelImpl.previewModel(ctx),
                regionViewModel = RegionViewModelImpl.previewModel(ctx),
                regionDistrictsListViewModel = RegionDistrictsListViewModelImpl.previewModel(ctx),
                regionDistrictViewModel = RegionDistrictViewModelImpl.previewModel(ctx),
                inputWrapper = InputListItemWrapper(),
                onValueChange = {},
                onImeKeyAction = {}
            )*/
        }
    }
}
