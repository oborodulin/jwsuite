package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.member.list

import android.content.res.Configuration
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.oborodulin.home.common.ui.ComponentUiAction
import com.oborodulin.home.common.ui.components.list.EmptyListTextComponent
import com.oborodulin.home.common.ui.components.list.items.ListItemComponent
import com.oborodulin.home.common.ui.state.CommonScreen
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.CongregationInput
import com.oborodulin.jwsuite.presentation.navigation.NavigationInput.GroupInput
import com.oborodulin.jwsuite.presentation.ui.AppState
import com.oborodulin.jwsuite.presentation.ui.theme.JWSuiteTheme
import com.oborodulin.jwsuite.presentation_congregation.R
import com.oborodulin.jwsuite.presentation_congregation.ui.model.MembersListItem
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.util.UUID

private const val TAG = "Congregating.MembersListView"

@Composable
fun MembersListView(
    appState: AppState,
    //sharedViewModel: SharedViewModeled<CongregationsListItem?>,
    viewModel: MembersListViewModelImpl = hiltViewModel(),
    congregationInput: CongregationInput? = null,
    groupInput: GroupInput? = null
) {
    Timber.tag(TAG).d(
        "MembersListView(...) called: congregationInput = %s; groupInput = %s",
        congregationInput,
        groupInput
    )
    val currentCongregation =
        appState.sharedViewModel.value?.sharedFlow?.collectAsStateWithLifecycle()?.value

    val congregationId = congregationInput?.congregationId ?: currentCongregation?.itemId
    Timber.tag(TAG)
        .d("currentCongregation = %s; congregationId = %s", currentCongregation, congregationId)

    LaunchedEffect(congregationId, groupInput?.groupId) {
        Timber.tag(TAG).d("MembersListView: LaunchedEffect() BEFORE collect ui state flow")
        when (groupInput?.groupId) {
            null -> viewModel.submitAction(MembersListUiAction.LoadByCongregation(congregationId))
            else -> viewModel.submitAction(MembersListUiAction.LoadByGroup(groupInput.groupId))
        }
    }
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()
    viewModel.uiStateFlow.collectAsStateWithLifecycle().value.let { state ->
        Timber.tag(TAG).d("Collect ui state flow: %s", state)
        CommonScreen(state = state) {
            MembersList(
                congregationId = congregationId,
                groupId = groupInput?.groupId,
                searchedText = searchText.text,
                members = it,
                onEdit = { member -> viewModel.submitAction(MembersListUiAction.EditMember(member.id)) },
                onDelete = { member ->
                    viewModel.submitAction(MembersListUiAction.DeleteMember(member.id))
                }
            ) { member -> viewModel.singleSelectItem(member) }
        }
    }
    LaunchedEffect(Unit) {
        Timber.tag(TAG).d("MembersListView: LaunchedEffect() AFTER collect single Event Flow")
        viewModel.singleEventFlow.collectLatest {
            Timber.tag(TAG).d("Collect Latest UiSingleEvent: %s", it.javaClass.name)
            when (it) {
                is MembersListUiSingleEvent.OpenMemberScreen -> {
                    appState.mainNavigate(it.navRoute)
                }
            }
        }
    }
}

@Composable
fun MembersList(
    congregationId: UUID?,
    groupId: UUID?,
    searchedText: String = "",
    members: List<MembersListItem>,
    onEdit: (MembersListItem) -> Unit,
    onDelete: (MembersListItem) -> Unit,
    onClick: (MembersListItem) -> Unit
) {
    Timber.tag(TAG).d("MembersList(...) called: size = %d", members.size)
    if (members.isNotEmpty()) {
        var filteredItems: List<MembersListItem>
        val listState =
            rememberLazyListState(initialFirstVisibleItemIndex = members.filter { it.selected }
                .getOrNull(0)?.let { members.indexOf(it) } ?: 0)
        LazyColumn(
            state = listState,
            modifier = Modifier
                .selectableGroup() // Optional, for accessibility purpose
                .padding(8.dp)
                .focusable(enabled = true)
        ) {
            filteredItems = if (searchedText.isEmpty()) {
                members
            } else {
                members.filter { it.doesMatchSearchQuery(searchedText) }
            }
            itemsIndexed(filteredItems, key = { _, item -> item.id }) { _, member ->
                ListItemComponent(
                    item = member,
                    itemActions = listOf(
                        ComponentUiAction.EditListItem { onEdit(member) },
                        ComponentUiAction.DeleteListItem(
                            stringResource(R.string.dlg_confirm_del_member, member.headline)
                        ) { onDelete(member) }),
                    selected = member.selected,
                    onClick = { onClick(member) }
                )
            }
        }
    } else {
        if (congregationId != null || groupId != null) {
            EmptyListTextComponent(R.string.members_list_empty_text)
        }
    }
}

@Preview(name = "Night Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(name = "Day Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewMembersList() {
    JWSuiteTheme {
        Surface {
            MembersList(
                congregationId = UUID.randomUUID(),
                groupId = UUID.randomUUID(),
                members = MembersListViewModelImpl.previewList(LocalContext.current),
                onEdit = {},
                onDelete = {}
            ) {}
        }
    }
}
