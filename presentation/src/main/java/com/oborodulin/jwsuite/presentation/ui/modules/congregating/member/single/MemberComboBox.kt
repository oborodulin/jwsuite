package com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.single

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
import com.oborodulin.jwsuite.presentation.ui.modules.FavoriteCongregationViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.FavoriteCongregationViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.list.MembersListUiAction
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.list.MembersListViewModel
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.member.list.MembersListViewModelImpl
import com.oborodulin.jwsuite.presentation.ui.modules.congregating.model.CongregationsListItem
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import timber.log.Timber

private const val TAG = "Congregating.MemberComboBox"

@Composable
fun MemberComboBox(
    modifier: Modifier = Modifier,
    sharedViewModel: FavoriteCongregationViewModel<CongregationsListItem>,
    listViewModel: MembersListViewModel,
    singleViewModel: MemberViewModel,
    inputWrapper: InputListItemWrapper,
    onValueChange: OnListItemEvent,
    onImeKeyAction: OnImeKeyAction
) {
    Timber.tag(TAG).d("MemberComboBox(...) called")
    var isShowListDialog by remember { mutableStateOf(false) }
    val onShowListDialog = { isShowListDialog = true }
    val onDismissListDialog = { isShowListDialog = false }
    val isShowNewSingleDialog by singleViewModel.showDialog.collectAsStateWithLifecycle()
    FullScreenDialog(
        isShow = isShowNewSingleDialog,
        viewModel = singleViewModel,
        loadUiAction = MemberUiAction.Load(),
        confirmUiAction = MemberUiAction.Save,
        dialogView = { MemberView(sharedViewModel, singleViewModel) },
        onValueChange = onValueChange,
        //onShowListDialog = onShowListDialog
    )
    val currentCongregation by sharedViewModel.sharedFlow.collectAsStateWithLifecycle(null)
    ComboBoxComponent(
        modifier = modifier,
        listViewModel = listViewModel,
        loadListUiAction = MembersListUiAction.LoadByCongregation(currentCongregation?.id),
        searchedItem = "",
        isShowListDialog = isShowListDialog,
        onShowListDialog = onShowListDialog,
        onDismissListDialog = onDismissListDialog,
        onShowSingleDialog = { singleViewModel.onOpenDialogClicked() },
        labelResId = R.string.member_hint,
        listTitleResId = R.string.dlg_title_select_group,
        leadingIcon = { Icon(painterResource(R.drawable.ic_person_36), null) },
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
            MemberComboBox(
                sharedViewModel = FavoriteCongregationViewModelImpl.previewModel,
                listViewModel = MembersListViewModelImpl.previewModel(ctx),
                singleViewModel = MemberViewModelImpl.previewModel(ctx),
                inputWrapper = InputListItemWrapper(),
                onValueChange = {},
                onImeKeyAction = {}
            )
        }
    }
}
