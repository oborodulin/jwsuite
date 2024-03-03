package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.list

import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.list.EditableListViewComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.home.common.util.LogLevel.LOG_UI_STATE
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.CongregationInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.GroupInput
import com.oborodulin.jwsuite.presentation.ui.LocalAppState
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_congregation.R
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list.MembersWithUsernameUiAction
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list.MembersWithUsernameViewModelImpl
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Congregating.GroupsListView"

@Composable
fun GroupsListView(
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    groupsListViewModel: GroupsListViewModelImpl = hiltViewModel(),
    membersListViewModel: MembersWithUsernameViewModelImpl = hiltViewModel(),
    congregationInput: CongregationInput? = null,
    groupInput: GroupInput? = null
) {
    val appState = LocalAppState.current
    Timber.tag(TAG).d(
        "GroupsListView(...) called: congregationInput = %s; groupInput = %s",
        congregationInput,
        groupInput
    )
    //val lifecycleOwner = LocalLifecycleOwner.current
    //var currentCongregation = remember(sharedViewModel.sharedFlow, lifecycleOwner) {
    //    sharedViewModel.sharedFlow.flowWithLifecycle(
    //        lifecycleOwner.lifecycle,
    //        Lifecycle.State.STARTED
    //    )
    //}
    val currentCongregation =
        appState.congregationSharedViewModel.value?.sharedFlow?.collectAsStateWithLifecycle()?.value
    val congregationId = congregationInput?.congregationId ?: currentCongregation?.itemId
    Timber.tag(TAG)
        .d(
            "currentCongregation = %s; congregationId = %s", currentCongregation, congregationId
        )
    LaunchedEffect(congregationId) {
        Timber.tag(TAG).d("GroupsListView -> LaunchedEffect()")
        when (congregationId) {
            null -> groupsListViewModel.submitAction(GroupsListUiAction.Load())
            else -> groupsListViewModel.submitAction(GroupsListUiAction.Load(congregationId))
        }
    }
    val searchText by groupsListViewModel.searchText.collectAsStateWithLifecycle()
    groupsListViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        if (LOG_UI_STATE) Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            EditableListViewComponent(
                items = it,
                searchedText = searchText.text,
                dlgConfirmDelResId = R.string.dlg_confirm_del_group,
                emptyListResId = R.string.groups_list_empty_text,
                isEmptyListTextOutput = (congregationInput?.congregationId
                    ?: currentCongregation?.itemId) != null,
                onEdit = { group ->
                    groupsListViewModel.submitAction(GroupsListUiAction.EditGroup(group.itemId!!))
                },
                onDelete = { group ->
                    groupsListViewModel.submitAction(GroupsListUiAction.DeleteGroup(group.itemId!!))
                }
            ) { group ->
                groupsListViewModel.singleSelectItem(group)
                with(membersListViewModel) {
                    submitAction(MembersWithUsernameUiAction.LoadByGroup(groupId = group.itemId!!))
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("GroupsListView -> LaunchedEffect() -> collect single Event Flow")
        groupsListViewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is GroupsListUiSingleEvent.OpenGroupScreen -> {
                    appState.mainNavigate(it.navRoute)
                }
            }
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewGroupsCongregating() {
    JWSuiteTheme {
        Surface {
            /*GroupsList(
                congregationId = UUID.randomUUID(),
                groups = GroupsListViewModelImpl.previewList(LocalContext.current),
                onClick = {},
                onEdit = {},
                onDelete = {}
            )*/
        }
    }
}
