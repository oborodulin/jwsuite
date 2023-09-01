package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.single

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.oborodulin.jwsuite.presentation_congregation.R
import com.oborodulin.jwsuite.presentation_congregation.ui.FavoriteCongregationViewModel
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list.MembersListUiAction
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list.MembersListViewModelImpl
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem
import timber.log.Timber

private const val TAG = "Congregating.BarMemberComboBox"

@Composable
fun BarMemberComboBox(
    modifier: Modifier = Modifier,
    sharedViewModel: FavoriteCongregationViewModel<CongregationsListItem?>,
    listViewModel: MembersListViewModelImpl = hiltViewModel(),
    singleViewModel: MemberViewModelImpl = hiltViewModel(),
    inputWrapper: InputListItemWrapper<ListItemModel>,
    onValueChange: OnListItemEvent,
    onImeKeyAction: OnImeKeyAction = {}
) {
    Timber.tag(TAG).d("BarMemberComboBox(...) called")
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
    val currentCongregation = sharedViewModel?.sharedFlow?.collectAsStateWithLifecycle()?.value
    Timber.tag(TAG).d("currentCongregation = %s", currentCongregation)
    BarComboBoxComponent(
        modifier = modifier,
        listViewModel = listViewModel,
        loadListUiAction = MembersListUiAction.LoadByCongregation(currentCongregation?.id),
        isShowListDialog = isShowListDialog,
        onShowListDialog = onShowListDialog,
        onDismissListDialog = onDismissListDialog,
        onShowSingleDialog = { singleViewModel.onOpenDialogClicked() },
        placeholderResId = R.string.member_placeholder,
        listTitleResId = R.string.dlg_title_select_member,
        leadingImageVector = Icons.Outlined.Person,
        inputWrapper = inputWrapper,
        onValueChange = onValueChange,
        onImeKeyAction = onImeKeyAction
    )
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewBarMemberComboBox() {
    /*val ctx = LocalContext.current
    JWSuiteTheme {
        Surface {
            BarMemberComboBox(
                sharedViewModel = FavoriteCongregationViewModelImpl.previewModel,
                listViewModel = MembersListViewModelImpl.previewModel(ctx),
                singleViewModel = MemberViewModelImpl.previewModel(ctx),
                inputWrapper = InputListItemWrapper(),
                onValueChange = {},
                onImeKeyAction = {}
            )
        }
    }*/
}
