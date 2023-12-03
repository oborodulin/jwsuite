package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list

import android.content.res.Configuration
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.components.list.EditableListViewComponent
import com.oborodulin.home.common.ui.components.list.ListViewComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.CongregationInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.GroupInput
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_congregation.R
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.role.list.MemberRolesListUiAction
import com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.role.list.MemberRolesListViewModelImpl
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

private const val TAG = "Congregating.MembersListView"

@Composable
fun MembersListView(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    membersListViewModel: MembersListViewModelImpl = hiltViewModel(),
    memberRolesListViewModel: MemberRolesListViewModelImpl = hiltViewModel(),
    congregationInput: CongregationInput? = null,
    groupInput: GroupInput? = null,
    isService: Boolean = false,
    isEditableList: Boolean = true
) {
    Timber.tag(TAG).d(
        "MembersListView(...) called: congregationInput = %s; groupInput = %s; isService = %s",
        congregationInput, groupInput, isService
    )
    val currentCongregation =
        appState.sharedViewModel.value?.sharedFlow?.collectAsStateWithLifecycle()?.value

    val congregationId = congregationInput?.congregationId ?: currentCongregation?.itemId
    Timber.tag(TAG)
        .d("currentCongregation = %s; congregationId = %s", currentCongregation, congregationId)

    LaunchedEffect(congregationId, groupInput?.groupId, isService) {
        Timber.tag(TAG).d("MembersListView -> LaunchedEffect() BEFORE collect ui state flow")
        when (groupInput?.groupId) {
            null -> membersListViewModel.submitAction(
                MembersListUiAction.LoadByCongregation(congregationId, isService)
            )

            else -> membersListViewModel.submitAction(
                MembersListUiAction.LoadByGroup(groupInput.groupId, isService)
            )
        }
    }
    val searchText by membersListViewModel.searchText.collectAsStateWithLifecycle()
    membersListViewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            when (isEditableList) {
                true -> {
                    EditableListViewComponent(
                        items = it,
                        searchedText = searchText.text,
                        dlgConfirmDelResId = R.string.dlg_confirm_del_member,
                        emptyListResId = R.string.members_list_empty_text,
                        isEmptyListTextOutput = congregationId != null || groupInput?.groupId != null,
                        onEdit = { member ->
                            membersListViewModel.submitAction(MembersListUiAction.EditMember(member.itemId!!))
                        },
                        onDelete = { member ->
                            membersListViewModel.submitAction(
                                MembersListUiAction.DeleteMember(member.itemId!!)
                            )
                        }
                    ) { member ->
                        membersListViewModel.singleSelectItem(member)
                        with(memberRolesListViewModel) {
                            submitAction(MemberRolesListUiAction.Load(member.itemId!!))
                        }
                    }
                }

                false -> {
                    ListViewComponent(
                        items = it,
                        emptyListResId = R.string.members_list_empty_text,
                        isEmptyListTextOutput = congregationId != null || groupInput?.groupId != null
                    )
                }
            }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("MembersListView -> LaunchedEffect() AFTER collect single Event Flow")
        membersListViewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is MembersListUiSingleEvent.OpenMemberScreen -> {
                    appState.mainNavigate(it.navRoute)
                }
            }
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewMembersList() {
    JWSuiteTheme {
        Surface {
            /*MembersList(
                congregationId = UUID.randomUUID(),
                groupId = UUID.randomUUID(),
                members = MembersListViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {}
            ) {}*/
        }
    }
}
